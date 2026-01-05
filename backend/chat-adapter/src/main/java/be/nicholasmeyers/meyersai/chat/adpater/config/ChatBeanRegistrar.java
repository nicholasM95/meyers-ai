package be.nicholasmeyers.meyersai.chat.adpater.config;

import be.nicholasmeyers.meyersai.chat.usecase.SendChatMessageUseCase;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.core.env.Environment;

public class ChatBeanRegistrar implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, @NonNull Environment env) {
        registry.registerBean(SendChatMessageUseCase.class);
    }
}
