package io.github.defective4.matrix.client.matrix.model.request;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public record CreateRoomRequest(String visibility, String name, String topic, List<String> invite,
        @SerializedName("is_direct") boolean direct) {
}
