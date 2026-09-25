package io.github.defective4.matrix.client.matrix.event;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class ClientEvent {
    private final JsonObject content;
    @SerializedName("event_id")
    private final String eventId;
    private final String sender;
    @SerializedName("state_key")
    private final String stateKey;
    private final String type;

    public ClientEvent(ClientEvent parent) {
        this(parent.eventId, parent.type, parent.sender, parent.content, parent.stateKey);
    }

    public ClientEvent(String eventId, String type, String sender, JsonObject content, String stateKey) {
        this.eventId = eventId;
        this.type = type;
        this.sender = sender;
        this.content = content;
        this.stateKey = stateKey;
    }

    public JsonObject getContent() {
        return content;
    }

    public <T> T getContentAs(Class<T> type, Gson gson) {
        return gson.fromJson(content, type);
    }

    public String getEventId() {
        return eventId;
    }

    public String getSenderId() {
        return sender;
    }

    public String getStateKey() {
        return stateKey;
    }

    public String getType() {
        return type;
    }
}
