package be.nicholasmeyers.meyersai.chat.domain;


import java.util.Optional;

// TODO use the domain, so we can do validation on messages before going to the gateway
public class ChatMessage {

    private final String message;

    protected ChatMessage(ChatMessageCreateRequest chatMessageCreateRequest) {
        this.message = Optional.ofNullable(chatMessageCreateRequest.message()).orElse("");
    }

    public void validate() {
        if (message.isBlank()) throw new ChatMessageCreateException("Message cannot be empty");
        if (message.length() < 2) throw new ChatMessageCreateException("Message must be at least 2 characters long");
    }

    public String getMessage() {
        return message;
    }
}
