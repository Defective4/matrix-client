package io.github.defective4.matrix.client.matrix.entity;

import com.google.gson.annotations.SerializedName;

import io.github.defective4.matrix.client.matrix.event.ClientEvent;

public record EventRelationship(@SerializedName("event_id") String eventId, String key,
        @SerializedName("rel_type") String relType) {
    public static final String KEY = "m.relates_to";
    public static final String M_ANNOTATION = "m.annotation";
    public static final String M_REACTION = "m.reaction";

    public ClientEvent getAsEvent() {
        return new ClientEvent(eventId, null, null, null, null);
    }
}
