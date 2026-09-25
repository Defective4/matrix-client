package io.github.defective4.matrix.client.matrix.model.request;

import com.google.gson.annotations.SerializedName;

public record RoomRequest(@SerializedName("user_id") String user, String reason) {
}
