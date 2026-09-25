package io.github.defective4.matrix.client.matrix;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.google.gson.JsonObject;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.http.HttpClient;
import io.github.defective4.matrix.client.matrix.entity.Room;
import io.github.defective4.matrix.client.matrix.entity.user.User;
import io.github.defective4.matrix.client.matrix.event.listener.EventListener;
import io.github.defective4.matrix.client.matrix.model.request.CreateRoomRequest;

public class MatrixClient {
    final HttpClient client;
    private boolean connected;
    private final Random random = new Random();
    private User selfUser;
    private final MatrixSynchronizer synchronizer = new MatrixSynchronizer(this);

    public MatrixClient(URL baseURL, char[] token) {
        client = new HttpClient(baseURL, token);
    }

    public boolean addListener(EventListener listener) {
        return synchronizer.addListener(this, listener);
    }

    public synchronized void connect() throws IOException {
        if (connected) throw new IllegalStateException("Already connected");
        selfUser = new User(this, whoami());
        synchronizer.startSyncThread(this);
        connected = true;
    }

    public Room createRoom(String name, Room.Visibility visibility, String topic) throws IOException {
        return new Room(this,
                makeRequest("/createRoom",
                        new CreateRoomRequest(visibility.name().toLowerCase(), name, topic, List.of(), false),
                        JsonObject.class, HTTPMethod.POST).get("room_id").getAsString());
    }

    public HttpClient getHttpClient() {
        return client;
    }

    public List<EventListener> getListeners() {
        return synchronizer.getListeners(this);
    }

    public Random getRandom() {
        return random;
    }

    public Room getRoomById(String id) {
        return new Room(this, id);
    }

    public User getSelfUser() {
        return selfUser;
    }

    public User getUserById(String id) {
        return new User(this, id);
    }

    public <T> T makeRequest(String path, Object body, Class<T> type, HTTPMethod method)
            throws MalformedURLException, IOException {
        return makeRequest(path, body, type, method, null);
    }

    public <T> T makeRequest(String path, Object body, Class<T> type, HTTPMethod method,
            Consumer<HttpURLConnection> connectionModifier) throws MalformedURLException, IOException {
        if (!connected) throw new IllegalStateException("Not connected");
        return client.makeRequest(path, body, type, method, connectionModifier);
    }

    public boolean removeListener(EventListener listener) {
        return synchronizer.removeListener(this, listener);
    }

    private String whoami() throws IOException {
        return client.makeRequest("/account/whoami", null, JsonObject.class, HTTPMethod.GET).get("user_id")
                .getAsString();
    }
}
