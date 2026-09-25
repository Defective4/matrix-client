package io.github.defective4.matrix.client.matrix.model;

import com.google.gson.annotations.SerializedName;

public record SyncResponse(@SerializedName("next_batch") String nextBatch, Rooms rooms) {
    @Override
    public Rooms rooms() {
        return rooms == null ? new Rooms(null, null) : rooms;
    }
}
