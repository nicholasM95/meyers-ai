package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MockChatGateway implements ChatGateway {
    @Override
    public void sendChatMessage(ChatMessage message, Consumer<String> onChunk) {
        String[] words = "This is a response of Meyers-AI".split(" ");

        for (String word : words) {
            onChunk.accept(word + " ");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
