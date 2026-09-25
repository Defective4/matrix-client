package io.github.defective4.matrix.client.matrix.model.sync;

import com.google.gson.annotations.SerializedName;

public record InvitedRoom(@SerializedName("invite_state") InviteState state) {
    @Override
    public InviteState state() {
        return state == null ? new InviteState(null) : state;
    }
}
