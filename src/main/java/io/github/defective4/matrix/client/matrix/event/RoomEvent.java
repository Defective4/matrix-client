package io.github.defective4.matrix.client.matrix.event;

import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.user.User;

public class RoomEvent extends ClientEvent {

    private final Room room;
    private final User sender;

    public RoomEvent(ClientEvent parent, Room room, MatrixClient client) {
        super(parent);
        this.room = room;
        sender = new User(client, super.getSenderId());
    }

    public Room getRoom() {
        return room;
    }

    public User getSender() {
        return sender;
    }

}
