package be.nicholasmeyers.meyersai.chat.usecase;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessageCreateRequest;
import be.nicholasmeyers.meyersai.chat.domain.ChatMessageFactory;
import be.nicholasmeyers.meyersai.chat.gateway.ChatGateway;

import java.util.function.Consumer;

public record SendChatMessageUseCase(ChatGateway chatGateway) {

    public void sendChatMessage(ChatMessageCreateRequest message, Consumer<String> onChunk) {
        chatGateway.sendChatMessage(ChatMessageFactory.createChatMessage(message), onChunk);
    }
}
