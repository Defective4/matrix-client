package io.github.defective4.matrix.client.matrix.model;

import java.util.Map;

public record Rooms(Map<String, JoinedRoom> join) {
    @Override
    public Map<String, JoinedRoom> join() {
        return join == null ? Map.of() : join;
    }
}
