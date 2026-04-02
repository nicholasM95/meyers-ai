package be.nicholasmeyers.meyersai.chat.view;

import java.time.LocalDateTime;
import java.util.UUID;

public record ChatMessageView(UUID conversationId, String content, ChatTypeView type, LocalDateTime timestamp) {
}
