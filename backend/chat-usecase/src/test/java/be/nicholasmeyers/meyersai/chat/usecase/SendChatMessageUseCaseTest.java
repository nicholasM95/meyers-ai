package be.nicholasmeyers.meyersai.chat.usecase;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.domain.ChatMessageCreateRequest;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SendChatMessageUseCaseTest {

    @InjectMocks
    private SendChatMessageUseCase sendChatMessageUseCase;

    @Mock
    private ChatGateway chatGateway;

    @Nested
    class SendChatMessage {
        @Test
        void sendChatMessage() {
            // Given
            ChatMessageCreateRequest chatMessageCreateRequest = new ChatMessageCreateRequest("Hello world");
            Consumer<String> onNext = _ -> {};
            Runnable onComplete = () -> {};
            Consumer<Throwable> onError = _ -> {};

            // When
            sendChatMessageUseCase.sendChatMessage(chatMessageCreateRequest, onNext, onComplete, onError);

            // Then
            ArgumentCaptor<ChatMessage> chatMessageArgumentCaptor = ArgumentCaptor.forClass(ChatMessage.class);
            verify(chatGateway).sendChatMessage(chatMessageArgumentCaptor.capture(), eq(onNext), eq(onComplete), eq(onError));
            assertThat(chatMessageArgumentCaptor.getValue().getMessage()).isEqualTo("Hello world");
        }
    }
}
