package io.github.defective4.matrix.client.matrix.event.listener;

import io.github.defective4.matrix.client.matrix.entity.EventRelationship;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.MessageEvent;
import io.github.defective4.matrix.client.matrix.event.RoomEvent;

public interface EventListener {
    void messageReceived(MessageEvent event);

    void reactionAdded(RoomEvent roomEvent, EventRelationship relationship);

    void userInvited(Room room, User sender, User invited);
}
