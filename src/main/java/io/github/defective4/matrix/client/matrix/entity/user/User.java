package io.github.defective4.matrix.client.matrix.entity.user;

import java.io.IOException;

import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.Entity;
import io.github.defective4.matrix.client.matrix.entity.Room;

public class User extends Entity {

    private final String id;

    public User(MatrixClient client, String id) {
        super(client);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void inviteToRoom(Room room, String reason) throws IOException {
        room.invite(this, reason);
    }

    public boolean isSelf() {
        return this == client.getSelfUser() || id.equals(client.getSelfUser().id);
    }

    @Override
    public String toString() {
        return "User [id=" + id + "]";
    }

}
