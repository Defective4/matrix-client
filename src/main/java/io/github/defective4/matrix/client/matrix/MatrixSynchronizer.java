package io.github.defective4.matrix.client.matrix;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonObject;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.message.TextMessage;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.ClientEvent;
import io.github.defective4.matrix.client.matrix.event.MemberEvent;
import io.github.defective4.matrix.client.matrix.event.MessageEvent;
import io.github.defective4.matrix.client.matrix.event.listener.EventListener;
import io.github.defective4.matrix.client.matrix.model.response.SyncResponse;

public class MatrixSynchronizer {
    final List<EventListener> listeners = new CopyOnWriteArrayList<>();
    final int syncInterval = 30;
    final ExecutorService syncService = Executors.newFixedThreadPool(1);
    String syncSince;
    private final MatrixClient client;

    public MatrixSynchronizer(MatrixClient client) {
        this.client = client;
    }

    public boolean addListener(MatrixClient matrixClient, EventListener listener) {
        return listeners.add(Objects.requireNonNull(listener));
    }

    public List<EventListener> getListeners(MatrixClient matrixClient) {
        return Collections.unmodifiableList(listeners);
    }

    public boolean removeListener(MatrixClient matrixClient, EventListener listener) {
        return listeners.remove(listener);
    }

    public SyncResponse sync(MatrixClient matrixClient, String since) throws IOException {
        String stateQuery = since == null ? "full_state=true"
                : "full_state=false&since=%s".formatted(URLEncoder.encode(since, StandardCharsets.UTF_8));
        JsonObject obj = matrixClient.makeRequest("/sync?%s&timeout=%s".formatted(stateQuery, syncInterval * 1000),
                null, JsonObject.class, HTTPMethod.GET, con -> con.setReadTimeout(Integer.MAX_VALUE));
        // System.out.println(obj);
        // System.err.println();
        return matrixClient.client.getGson().fromJson(obj, SyncResponse.class);
    }

    void handleInviteEvent(String roomId, ClientEvent event) {
        User sender = new User(client, event.getSenderId());
        if (sender.isSelf()) return;
        Room room = new Room(client, roomId);
        switch (event.getType()) {
            case Room.M_ROOM_MEMBER -> {
                MemberEvent memberEvent = event.getContentAs(MemberEvent.class, client.getHttpClient().getGson());
                switch (memberEvent.membership()) {
                    case MemberEvent.INVITE -> {
                        User invited = new User(client, event.getStateKey());
                        listeners.forEach(ls -> ls.userInvited(room, sender, invited));
                    }
                    default -> {}
                }
            }
            default -> {}
        }
    }

    void handleRoomEvent(String roomId, ClientEvent event) {
        User sender = new User(client, event.getSenderId());
        if (sender.isSelf()) return;
        Room room = new Room(client, roomId);
        switch (event.getType()) {
            case Room.M_ROOM_MESSAGE -> {
                JsonObject content = event.getContent();
                String msgtype = content.get("msgtype").getAsString();
                Class<? extends Message> messageClass = switch (msgtype) {
                    case TextMessage.TYPE -> TextMessage.class;
                    default -> Message.class;
                };
                Message message = event.getContentAs(messageClass, client.getHttpClient().getGson());
                MessageEvent me = new MessageEvent(event, room, message, client);
                listeners.forEach(ls -> ls.messageReceived(me));
            }
            default -> {}
        }
    }

    void startSyncThread(MatrixClient matrixClient) {
        syncService.submit(() -> {
            while (true) {
                try {
                    SyncResponse response = sync(matrixClient, syncSince);
                    if (syncSince != null) {
                        response.rooms().join().forEach((roomId, join) -> join.timeline().events()
                                .forEach(event -> handleRoomEvent(roomId, event)));
                        response.rooms().invite().forEach((roomId, invite) -> invite.state().events()
                                .forEach(event -> handleInviteEvent(roomId, event)));
                    }
                    syncSince = response.nextBatch();
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO
                    return;
                }
            }
        });
    }
}
