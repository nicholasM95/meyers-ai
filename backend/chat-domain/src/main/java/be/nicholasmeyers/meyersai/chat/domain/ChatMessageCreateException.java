package be.nicholasmeyers.meyersai.chat.domain;

public class ChatMessageCreateException extends RuntimeException {
    public ChatMessageCreateException(String message) {
        super(message);
    }
}
