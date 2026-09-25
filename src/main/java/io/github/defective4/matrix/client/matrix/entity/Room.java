package io.github.defective4.matrix.client.matrix.entity;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import com.google.gson.JsonObject;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.message.TextMessage;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.ClientEvent;
import io.github.defective4.matrix.client.matrix.model.JsonVoid;
import io.github.defective4.matrix.client.matrix.model.request.RoomRequest;

public class Room extends Entity {

    public enum Visibility {
        PRIVATE, PUBLIC;
    }

    public static final String M_ROOM_MEMBER = "m.room.member";
    public static final String M_ROOM_MESSAGE = "m.room.message";
    private final String id;

    public Room(MatrixClient client, String id) {
        super(client);
        this.id = id;
    }

    public void ban(User user, String reason) throws IOException {
        client.makeRequest("/rooms/%s/ban".formatted(getURLEncodedID()), new RoomRequest(user.getId(), reason),
                JsonVoid.class, HTTPMethod.POST);
    }

    public String getId() {
        return id;
    }

    public void invite(User user, String reason) throws IOException {
        client.makeRequest("/rooms/%s/invite".formatted(getURLEncodedID()), new RoomRequest(user.getId(), reason),
                JsonVoid.class, HTTPMethod.POST);
    }

    public void join() throws IOException {
        client.makeRequest("/rooms/%s/join".formatted(getURLEncodedID()), new JsonVoid(), JsonVoid.class,
                HTTPMethod.POST);
    }

    public void kick(User user, String reason) throws IOException {
        client.makeRequest("/rooms/%s/kick".formatted(getURLEncodedID()), new RoomRequest(user.getId(), reason),
                JsonVoid.class, HTTPMethod.POST);
    }

    public void leave(String reason) throws IOException {
        JsonObject root = new JsonObject();
        if (reason != null) root.addProperty("reason", reason);
        client.getHttpClient().makeRequest("/rooms/%s/leave".formatted(getURLEncodedID()), root, JsonVoid.class,
                HTTPMethod.POST);
    }

    public void react(ClientEvent event, String emoji) throws IOException {
        JsonObject root = new JsonObject();
        root.add(EventRelationship.KEY, client.getHttpClient().getGson()
                .toJsonTree(new EventRelationship(event.getEventId(), emoji, EventRelationship.M_ANNOTATION)));
        sendRoomEvent(EventRelationship.M_REACTION, root);
    }

    public void sendMessage(Message message) throws IOException {
        sendRoomEvent(M_ROOM_MESSAGE, message);
    }

    public void sendMessage(String message) throws IOException {
        sendMessage(new TextMessage(message));
    }

    public void sendRoomEvent(String eventType, Object event) throws IOException {
        String url = "/rooms/%s/send/%s/%s".formatted(getURLEncodedID(), eventType, client.getRandom().nextLong());
        client.makeRequest(url, event, JsonVoid.class, HTTPMethod.PUT);
    }

    @Override
    public String toString() {
        return "Room [id=" + id + "]";
    }

    public void unban(User user, String reason) throws IOException {
        client.makeRequest("/rooms/%s/unban".formatted(getURLEncodedID()), new RoomRequest(user.getId(), reason),
                JsonVoid.class, HTTPMethod.POST);
    }

    private String getURLEncodedID() {
        return URLEncoder.encode(id, StandardCharsets.UTF_8);
    }

}
