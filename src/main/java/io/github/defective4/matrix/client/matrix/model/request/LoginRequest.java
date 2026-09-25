package io.github.defective4.matrix.client.matrix.model.request;

import com.google.gson.annotations.SerializedName;

import io.github.defective4.matrix.client.matrix.model.UserIdentifier;

public record LoginRequest(UserIdentifier identifier, String password, @SerializedName("device_id") String deviceId,
        String type, @SerializedName("initial_device_display_name") String deviceName) {

    public static final String M_LOGIN_PASSWORD = "m.login.password";

}
