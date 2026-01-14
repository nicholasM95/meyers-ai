package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Profile("!local")
@Component
public class BedrockChatGateway implements ChatGateway {

    private static final Logger log = LoggerFactory.getLogger(BedrockChatGateway.class);
    private final ChatModel chatModel;

    public BedrockChatGateway(@Qualifier("bedrockProxyChatModel") ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public void sendChatMessage(ChatMessage message, Consumer<String> onChunk) {
        log.info(chatModel.call("hallo"));

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
