package io.github.defective4.matrix.client.matrix.entity.message;

public class TextMessage extends Message {

    public static final String TYPE = "m.text";
    private final String body;

    public TextMessage(String body) {
        super(TYPE);
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
