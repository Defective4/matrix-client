package io.github.defective4.matrix.client.matrix.event;

import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.user.User;

public abstract class EventAdapter implements EventListener {

    @Override
    public void messageReceived(User sender, Room room, Message message) {}

}
