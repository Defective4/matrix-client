package io.github.defective4.matrix.client.matrix.entity;

import io.github.defective4.matrix.client.matrix.MatrixClient;

public abstract class Entity {
    protected final MatrixClient client;

    protected Entity(MatrixClient client) {
        this.client = client;
    }

    public MatrixClient getClient() {
        return client;
    }

}
