package io.github.defective4.matrix.client.matrix;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.http.HttpClient;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.model.SyncResponse;

public class MatrixClient {
    private final HttpClient client;
    private final Random random = new Random();
    private final int syncInterval = 30;
    private final ExecutorService syncService = Executors.newFixedThreadPool(1);
    private String syncSince;

    public MatrixClient(URL baseURL, char[] token) {
        client = new HttpClient(baseURL, token);
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

    public void startSyncThread() {
        syncService.submit(() -> {
            while (true) {
                try {
                    SyncResponse response = sync(syncSince);
                    System.out.println(response);
                    syncSince = response.nextBatch();
                } catch (Exception e) {
                    e.printStackTrace();
                    // TODO
                    return;
                }
            }
        });
    }

    public SyncResponse sync(String since) throws IOException {
        String stateQuery = since == null ? "full_state=true"
                : "full_state=false&since=%s".formatted(URLEncoder.encode(since, StandardCharsets.UTF_8));
        return client.makeRequest("/client/v3/sync?%s&timeout=%s".formatted(stateQuery, syncInterval * 1000), null,
                SyncResponse.class, HTTPMethod.GET, con -> con.setReadTimeout(Integer.MAX_VALUE));
    }
}
