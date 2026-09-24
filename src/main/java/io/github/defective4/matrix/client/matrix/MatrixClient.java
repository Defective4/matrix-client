package io.github.defective4.matrix.client.matrix;

import java.util.Random;

import io.github.defective4.matrix.client.http.HttpClient;
import io.github.defective4.matrix.client.matrix.entity.Room;

public class MatrixClient {
    private final HttpClient client;
    private final Random random = new Random();

    public MatrixClient(HttpClient client) {
        this.client = client;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    public Random getRandom() {
        return random;
    }

    public Room getRoomById(String id) {
        return new Room(this, id);
    }
}
