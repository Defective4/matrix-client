package io.github.defective4.matrix.client.matrix.model;

public record UserIdentifier(String type, String user) {
    public static final String M_ID_USER = "m.id.user";

    public UserIdentifier(String user) {
        this("m.id.user", user);
    }
}
