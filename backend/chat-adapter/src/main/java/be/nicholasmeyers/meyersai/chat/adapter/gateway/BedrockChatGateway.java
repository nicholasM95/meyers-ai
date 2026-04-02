package be.nicholasmeyers.meyersai.chat.adapter.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Profile("!local")
@Component
public class BedrockChatGateway implements ChatGateway {

    private static final Logger log = LoggerFactory.getLogger(BedrockChatGateway.class);
    private static final String CHAT_MEMORY_CONVERSATION_ID_KEY = "chat_memory_conversation_id";

    private final ChatClient chatClient;


    public BedrockChatGateway(@Qualifier("bedrockProxyChatModel") ChatModel chatModel, ChatMemory chatMemory) {
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Override
    public void sendChatMessage(ChatMessage message, Consumer<String> onNext, Runnable onComplete, Consumer<Throwable> onError) {
        log.info("Sending message to Bedrock: {}", message.getMessage());

        chatClient.prompt()
                .user(message.getMessage())
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, message.getConversationId()))
                .stream()
                .content()
                .doOnNext(onNext)
                .doOnComplete(onComplete)
                .doOnError(onError)
                .subscribe();
    }
}
