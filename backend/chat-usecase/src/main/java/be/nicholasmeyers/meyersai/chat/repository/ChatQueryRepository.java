package be.nicholasmeyers.meyersai.chat.repository;

import be.nicholasmeyers.meyersai.chat.view.ChatMessageView;

import java.util.List;
import java.util.UUID;

public interface ChatQueryRepository {

    List<ChatMessageView> findMessagesByConversationId(UUID conversationId);

    List<UUID> findAllChatIds();
}
