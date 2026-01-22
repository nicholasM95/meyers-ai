package be.nicholasmeyers.meyersai.chat.adpater.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
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
    public void sendChatMessage(ChatMessage message, Consumer<String> onNext, Runnable onComplete, Consumer<Throwable> onError) {
        log.info("Sending message to Bedrock: {}", message.getMessage());

        chatModel.stream(new Prompt(message.getMessage()))
                .doOnNext(chatResponse -> onNext.accept(chatResponse.getResult().getOutput().getText()))
                .doOnComplete(onComplete)
                .doOnError(onError)
                .subscribe();
    }
}
