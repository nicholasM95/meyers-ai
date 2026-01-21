package be.nicholasmeyers.meyersai.chat.gateway;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessage;

import java.util.function.Consumer;

public interface ChatGateway {

    void sendChatMessage(ChatMessage message, Consumer<String> onNext, Runnable onComplete, Consumer<Throwable> onError);
}
