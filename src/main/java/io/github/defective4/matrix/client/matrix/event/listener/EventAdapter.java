package io.github.defective4.matrix.client.matrix.event.listener;

import io.github.defective4.matrix.client.matrix.entity.EventRelationship;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.MessageEvent;
import io.github.defective4.matrix.client.matrix.event.RoomEvent;

public abstract class EventAdapter implements EventListener {

    @Override
    public void messageReceived(MessageEvent event) {}

    @Override
    public void reactionAdded(RoomEvent roomEvent, EventRelationship relationship) {}

    @Override
    public void userInvited(Room room, User sender, User invited) {}
}
