package io.github.defective4.matrix.client.matrix.model.sync;

import java.util.List;

import io.github.defective4.matrix.client.matrix.event.ClientEvent;

public record InviteState(List<ClientEvent> events) {
    @Override
    public List<ClientEvent> events() {
        return events == null ? List.of() : events;
    }
}
