package be.nicholasmeyers.meyersai.chat.domain;


import java.util.Optional;
import java.util.UUID;

public class ChatMessage {

    private final UUID conversationId;
    private final String message;

    protected ChatMessage(ChatMessageCreateRequest chatMessageCreateRequest) {
        this.conversationId = UUID.fromString("fc51906c-6faa-43f0-a33a-08247553cea0");
        this.message = Optional.ofNullable(chatMessageCreateRequest.message()).orElse("");
    }

    public void validate() {
        if (message.isBlank()) throw new ChatMessageCreateException("Message cannot be empty");
        if (message.length() < 2) throw new ChatMessageCreateException("Message must be at least 2 characters long");
    }

    public String getMessage() {
        return message;
    }

    public UUID getConversationId() {
        return conversationId;
    }
}
