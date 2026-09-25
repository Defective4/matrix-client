package io.github.defective4.matrix.client.matrix.model.response;

import com.google.gson.annotations.SerializedName;

public record AuthResponse(@SerializedName("user_id") String userId, @SerializedName("access_token") String token,
        @SerializedName("device_id") String deviceId) {

}
