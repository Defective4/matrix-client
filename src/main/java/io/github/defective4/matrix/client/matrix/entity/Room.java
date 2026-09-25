package io.github.defective4.matrix.client.matrix.entity;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.message.TextMessage;
import io.github.defective4.matrix.client.matrix.model.JsonVoid;

public class Room extends Entity {
    public static final String M_ROOM_MEMBER = "m.room.member";
    public static final String M_ROOM_MESSAGE = "m.room.message";
    private final String id;

    public Room(MatrixClient client, String id) {
        super(client);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void sendMessage(Message message) throws IOException {
        sendRoomEvent(M_ROOM_MESSAGE, message);
    }

    public void sendMessage(String message) throws IOException {
        sendMessage(new TextMessage(message));
    }

    public void sendRoomEvent(String eventType, Object event) throws IOException {
        String url = "/client/v3/rooms/%s/send/%s/%s".formatted(URLEncoder.encode(id, StandardCharsets.UTF_8),
                eventType, client.getRandom().nextLong());
        client.makeRequest(url, event, JsonVoid.class, HTTPMethod.PUT);
    }

    @Override
    public String toString() {
        return "Room [id=" + id + "]";
    }

}
