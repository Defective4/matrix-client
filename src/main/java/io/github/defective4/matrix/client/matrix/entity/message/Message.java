package io.github.defective4.matrix.client.matrix.entity.message;

public abstract class Message {
    private final String msgtype;

    protected Message(String msgtype) {
        this.msgtype = msgtype;
    }
}
