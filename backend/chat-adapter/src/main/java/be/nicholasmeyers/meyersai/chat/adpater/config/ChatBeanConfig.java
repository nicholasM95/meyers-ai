package be.nicholasmeyers.meyersai.chat.adpater.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ChatBeanRegistrar.class)
public class ChatBeanConfig {

}
