package io.github.defective4.matrix.client.http;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpClient {

    private final String baseURL;
    private final Gson gson = new Gson();
    private final char[] token;

    public HttpClient(URL baseURL) {
        this(baseURL, null);
    }

    public HttpClient(URL baseURL, char[] token) {
        String u = baseURL.toString();
        while (u.endsWith("/")) u = u.substring(0, u.length() - 1);
        this.baseURL = u;
        this.token = token;
    }

    public <T> T makeRequest(String path, Object body, Class<T> type, HTTPMethod method)
            throws MalformedURLException, IOException {
        HttpURLConnection connection = (HttpURLConnection) URI.create(baseURL + "/_matrix" + path).toURL()
                .openConnection();
        try {
            connection.setRequestMethod(method.name());
            connection.setRequestProperty("Content-Type", "application/json");
            if (token != null) {
                connection.setRequestProperty("Authorization", "Bearer %s".formatted(new String(token)));
            }
            connection.setDoOutput(true);
            try (Writer writer = new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8)) {
                writer.write(gson.toJson(body));
            }

            try (Reader reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
                JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
                return gson.fromJson(object, type);
            }
        } finally {
            connection.disconnect();
        }
    }

}
