package io.github.defective4.matrix.client.matrix.event;

import io.github.defective4.matrix.client.matrix.MatrixClient;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.message.Message;

public class MessageEvent extends RoomEvent {

    private final Message message;

    public MessageEvent(ClientEvent parent, Room room, Message message, MatrixClient client) {
        super(parent, room, client);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

}
