package io.github.defective4.matrix.client.matrix.model;

public record JoinedRoom(EventTimeline timeline) {
    @Override
    public EventTimeline timeline() {
        return timeline == null ? new EventTimeline(null) : timeline;
    }
}
