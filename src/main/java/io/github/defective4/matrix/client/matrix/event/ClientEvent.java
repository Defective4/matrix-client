package io.github.defective4.matrix.client.matrix.event;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public record ClientEvent(@SerializedName("event_id") String eventId, String type, String sender, JsonObject content,
        @SerializedName("state_key") String stateKey) {
    public <T> T getContentAs(Class<T> type, Gson gson) {
        return gson.fromJson(content, type);
    }
}
