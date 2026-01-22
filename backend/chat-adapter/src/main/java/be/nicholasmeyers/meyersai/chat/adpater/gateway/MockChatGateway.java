package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Profile("local")
@Component
public class MockChatGateway implements ChatGateway {
    @Override
    public void sendChatMessage(ChatMessage message, Consumer<String> onNext, Runnable onComplete, Consumer<Throwable> onError) {
        try {
            String[] words = "This is a response of Meyers-AI".split(" ");

            for (String word : words) {
                onNext.accept(word + " ");
                Thread.sleep(200);
            }

            onComplete.run();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            onError.accept(e);
        } catch (Exception e) {
            onError.accept(e);
        }
    }
}
