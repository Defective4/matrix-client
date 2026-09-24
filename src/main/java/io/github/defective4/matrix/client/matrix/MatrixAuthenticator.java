package io.github.defective4.matrix.client.matrix;

import java.io.IOException;
import java.net.MalformedURLException;

import io.github.defective4.matrix.client.http.HTTPMethod;
import io.github.defective4.matrix.client.http.HttpClient;
import io.github.defective4.matrix.client.matrix.model.AuthResponse;
import io.github.defective4.matrix.client.matrix.model.LoginRequest;

public class MatrixAuthenticator {
    private final HttpClient client;

    public MatrixAuthenticator(HttpClient client) {
        this.client = client;
    }

    public AuthResponse login(LoginRequest request) throws MalformedURLException, IOException {
        return client.makeRequest("/client/v3/login", request, AuthResponse.class, HTTPMethod.POST);
    }
}
