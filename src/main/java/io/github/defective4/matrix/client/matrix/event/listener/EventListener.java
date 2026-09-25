package io.github.defective4.matrix.client.matrix.event.listener;

import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.MessageEvent;

public interface EventListener {
    void messageReceived(MessageEvent event);

    void userInvited(Room room, User sender, User invited);
}
