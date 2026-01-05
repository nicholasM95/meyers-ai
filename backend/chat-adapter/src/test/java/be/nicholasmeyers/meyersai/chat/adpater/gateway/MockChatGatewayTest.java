package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class MockChatGatewayTest {

    @Nested
    class SendChatMessage {
        @Test
        void givenChunks_whenSendChatMessage_thenChunksReceived() {
            // Given
            List<String> receivedChunks = new ArrayList<>();
            Consumer<String> onChunk = receivedChunks::add;

            // When
            MockChatGateway mockChatGateway = new MockChatGateway();
            mockChatGateway.sendChatMessage(null, onChunk);

            // Then
            assertThat(receivedChunks).containsExactly("This ",
                    "is ",
                    "a ",
                    "response ",
                    "of ",
                    "Meyers-AI ");
        }
    }
}
