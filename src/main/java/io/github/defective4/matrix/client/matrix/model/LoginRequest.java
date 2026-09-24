package io.github.defective4.matrix.client.matrix.model;

import com.google.gson.annotations.SerializedName;

public record LoginRequest(UserIdentifier identifier, String password, @SerializedName("device_id") String deviceId,
        String type, @SerializedName("initial_device_display_name") String deviceName) {

}
