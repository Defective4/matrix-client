package io.github.defective4.matrix.client.matrix.entity.user;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonObject;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.Entity;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.model.request.CreateRoomRequest;

public class User extends Entity {

    private final String id;

    public User(MatrixClient client, String id) {
        super(client);
        this.id = id;
    }

    public Room createPrivateChat() throws IOException {
        return new Room(
                client, client
                        .makeRequest("/createRoom",
                                new CreateRoomRequest(Room.Visibility.PRIVATE.name().toLowerCase(), null, null,
                                        List.of(id), true),
                                JsonObject.class, HTTPMethod.POST)
                        .get("room_id").getAsString());
    }

    public String getId() {
        return id;
    }

    public boolean isSelf() {
        return this == client.getSelfUser() || id.equals(client.getSelfUser().id);
    }

    @Override
    public String toString() {
        return "User [id=" + id + "]";
    }

}
