package be.nicholasmeyers.meyersai.chat.domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ChatMessageFactoryTest {

    @Nested
    class CreateChatMessage {

        @Test
        void givenChatMessageCreateRequest_whenCreateChatMessage_thenChatMessageCreated() {
            // Given
            ChatMessageCreateRequest chatMessageCreateRequest = new ChatMessageCreateRequest("Hello world");

            // When
            ChatMessage chatMessage = ChatMessageFactory.createChatMessage(chatMessageCreateRequest);

            // Then
            assertThat(chatMessage).isNotNull();
            assertThat(chatMessage.getMessage()).isEqualTo("Hello world");
        }

        @ParameterizedTest
        @NullAndEmptySource
        void givenChatMessageCreateRequestEmptyMessage_whenCreateChatMessage_thenThrowException(String message) {
            // Given
            ChatMessageCreateRequest chatMessageCreateRequest = new ChatMessageCreateRequest(message);

            // When & Then
            assertThatThrownBy(() -> ChatMessageFactory.createChatMessage(chatMessageCreateRequest))
                    .isInstanceOf(ChatMessageCreateException.class)
                    .hasMessage("Message cannot be empty");
        }

        @Test
        void givenChatMessageCreateRequestMessageLength1_whenCreateChatMessage_thenThrowException() {
            // Given
            ChatMessageCreateRequest chatMessageCreateRequest = new ChatMessageCreateRequest("h");

            // When & Then
            assertThatThrownBy(() -> ChatMessageFactory.createChatMessage(chatMessageCreateRequest))
                    .isInstanceOf(ChatMessageCreateException.class)
                    .hasMessage("Message must be at least 2 characters long");
        }
    }
}
