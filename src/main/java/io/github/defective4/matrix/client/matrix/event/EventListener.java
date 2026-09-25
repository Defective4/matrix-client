package io.github.defective4.matrix.client.matrix.event;

import java.io.IOException;

import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.message.Message;
import io.github.defective4.matrix.client.matrix.entity.user.User;

public interface EventListener {
    void messageReceived(User sender, Room room, Message message);

    void userInvited(Room room, User sender, User invited) throws IOException;
}
