package io.github.defective4.matrix.client.matrix.model;

import java.util.List;

public record InviteState(List<ClientEvent> events) {
    @Override
    public List<ClientEvent> events() {
        return events == null ? List.of() : events;
    }
}
