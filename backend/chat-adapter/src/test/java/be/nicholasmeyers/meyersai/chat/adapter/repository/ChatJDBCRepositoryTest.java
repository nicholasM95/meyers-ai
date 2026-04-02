package be.nicholasmeyers.meyersai.chat.adapter.repository;

import be.nicholasmeyers.meyersai.chat.view.ChatMessageView;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static be.nicholasmeyers.meyersai.chat.view.ChatTypeView.ASSISTANT;
import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
public class ChatJDBCRepositoryTest {

    @Autowired
    private ChatJDBCRepository chatJDBCRepository;

    @DatabaseTest
    @Nested
    class FindMessagesByConversationId {
        @Sql(scripts = "test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @Test
        void givenConversationId_whenFindMessagesByConversationId_thenReturnMessages() {
            // Given
            UUID conversationId = UUID.fromString("b7906e70-e90f-42fd-8e8a-2198cea2ac64");

            // When
            List<ChatMessageView> messages = chatJDBCRepository.findMessagesByConversationId(conversationId);

            // Then
            assertThat(messages).containsExactly(new ChatMessageView(UUID.fromString("b7906e70-e90f-42fd-8e8a-2198cea2ac64"), 
                    "Hello!", ASSISTANT, LocalDateTime.of(2026, 4, 2, 22, 35, 0)));
        }
    }

    @DatabaseTest
    @Nested
    class FindAllChatIds {
        @Sql("test-data.sql")
        @Test
        void given_whenFindAllChatIds_thenReturnListOfChatIds() {
            // Given

            // When
            List<UUID> chatIds = chatJDBCRepository.findAllChatIds();

            // Then
            assertThat(chatIds).containsExactly(UUID.fromString("b7906e70-e90f-42fd-8e8a-2198cea2ac64"));
        }
    }
}
