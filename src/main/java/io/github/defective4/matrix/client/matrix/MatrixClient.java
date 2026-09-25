package io.github.defective4.matrix.client.matrix;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.google.gson.JsonObject;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.http.HttpClient;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.message.TextMessage;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.ClientEvent;
import io.github.defective4.matrix.client.matrix.event.EventListener;
import io.github.defective4.matrix.client.matrix.event.MemberEvent;
import io.github.defective4.matrix.client.matrix.model.SyncResponse;

public class MatrixClient {
    private final HttpClient client;
    private boolean connected;
    private final List<EventListener> listeners = new CopyOnWriteArrayList<>();
    private final Random random = new Random();
    private User selfUser;
    private final int syncInterval = 30;
    private final ExecutorService syncService = Executors.newFixedThreadPool(1);
    private String syncSince;

    public MatrixClient(URL baseURL, char[] token) {
        client = new HttpClient(baseURL, token);
    }

    public boolean addListener(EventListener listener) {
        return listeners.add(Objects.requireNonNull(listener));
    }

    public synchronized void connect() throws IOException {
        if (connected) throw new IllegalStateException("Already connected");
        selfUser = new User(this, whoami());
        startSyncThread();
        connected = true;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    public List<EventListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    public Random getRandom() {
        return random;
    }

    public Room getRoomById(String id) {
        return new Room(this, id);
    }

    public User getSelfUser() {
        return selfUser;
    }

    public <T> T makeRequest(String path, Object body, Class<T> type, HTTPMethod method)
            throws MalformedURLException, IOException {
        return makeRequest(path, body, type, method, null);
    }

    public boolean removeListener(EventListener listener) {
        return listeners.remove(listener);
    }

    public SyncResponse sync(String since) throws IOException {
        String stateQuery = since == null ? "full_state=true"
                : "full_state=false&since=%s".formatted(URLEncoder.encode(since, StandardCharsets.UTF_8));
        JsonObject obj = makeRequest("/sync?%s&timeout=%s".formatted(stateQuery, syncInterval * 1000), null,
                JsonObject.class, HTTPMethod.GET, con -> con.setReadTimeout(Integer.MAX_VALUE));
//        System.out.println(obj);
//        System.err.println();
        return client.getGson().fromJson(obj, SyncResponse.class);
    }

    private void handleInviteEvent(String roomId, ClientEvent event) {
        User sender = new User(this, event.sender());
        if (sender.isSelf()) return;
        Room room = new Room(this, roomId);
        switch (event.type()) {
            case Room.M_ROOM_MEMBER -> {
                MemberEvent memberEvent = event.getContentAs(MemberEvent.class, client.getGson());
                switch (memberEvent.membership()) {
                    case MemberEvent.INVITE -> {
                        User invited = new User(this, event.stateKey());
                        listeners.forEach(ls -> {
                            try {
                                ls.userInvited(room, sender, invited);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    default -> {}
                }
            }
            default -> {}
        }
    }

    private void handleRoomEvent(String roomId, ClientEvent event) {
        User sender = new User(this, event.sender());
        if (sender.isSelf()) return;
        Room room = new Room(this, roomId);
        switch (event.type()) {
            case Room.M_ROOM_MESSAGE -> {
                JsonObject content = event.content();
                String msgtype = content.get("msgtype").getAsString();
                Class<? extends Message> messageClass = switch (msgtype) {
                    case TextMessage.TYPE -> TextMessage.class;
                    default -> Message.class;
                };
                Message message = event.getContentAs(messageClass, client.getGson());
                listeners.forEach(ls -> ls.messageReceived(sender, room, message));
            }
            default -> {}
        }
    }

    private <T> T makeRequest(String path, Object body, Class<T> type, HTTPMethod method,
            Consumer<HttpURLConnection> connectionModifier) throws MalformedURLException, IOException {
        if (!connected) throw new IllegalStateException("Not connected");
        return client.makeRequest(path, body, type, method, connectionModifier);
    }

    private void startSyncThread() {
        syncService.submit(() -> {
            while (true) {
                try {
                    SyncResponse response = sync(syncSince);
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

    private String whoami() throws IOException {
        return client.makeRequest("/account/whoami", null, JsonObject.class, HTTPMethod.GET).get("user_id")
                .getAsString();
    }
}
