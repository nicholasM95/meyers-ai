package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class MockChatGatewayTest {

    @Nested
    class SendChatMessage {
        @Test
        void givenChunks_whenSendChatMessage_thenChunksReceived() throws InterruptedException {
            // Given
            List<String> receivedChunks = new ArrayList<>();
            Consumer<String> onNext = receivedChunks::add;
            CountDownLatch latch = new CountDownLatch(1);
            Runnable onComplete = latch::countDown;
            Consumer<Throwable> onError = _ -> latch.countDown();

            // When
            MockChatGateway mockChatGateway = new MockChatGateway();
            mockChatGateway.sendChatMessage(null, onNext, onComplete, onError);

            latch.await(5, TimeUnit.SECONDS);

            // Then
            assertThat(receivedChunks).containsExactly(
                    "This ",
                    "is ",
                    "a ",
                    "response ",
                    "of ",
                    "Meyers-AI "
            );
        }
    }
}
