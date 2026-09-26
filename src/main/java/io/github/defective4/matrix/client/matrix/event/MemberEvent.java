package io.github.defective4.matrix.client.matrix.event;

public record MemberEvent(String membership) {
    public static final String INVITE = "invite";
    public static final String LEAVE = "leave";
}
