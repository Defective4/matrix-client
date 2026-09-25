package io.github.defective4.matrix.client.matrix.model;

import java.util.Map;

public record Rooms(Map<String, JoinedRoom> join, Map<String, InvitedRoom> invite) {
    @Override
    public Map<String, JoinedRoom> join() {
        return join == null ? Map.of() : join;
    }

    @Override
    public Map<String, InvitedRoom> invite() {
        return invite == null ? Map.of() : invite;
    }
}
