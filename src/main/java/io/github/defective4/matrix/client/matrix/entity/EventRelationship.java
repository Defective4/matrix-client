package io.github.defective4.matrix.client.matrix.entity;

import com.google.gson.annotations.SerializedName;

public record EventRelationship(@SerializedName("event_id") String eventId, String key,
        @SerializedName("rel_type") String relType) {
    public static final String KEY = "m.relates_to";
    public static final String REL_TYPE_ANNOTATION = "m.annotation";
    public static final String M_REACTION = "m.reaction";
}
