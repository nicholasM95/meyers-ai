package be.nicholasmeyers.meyersai.application;

import org.springframework.ai.model.bedrock.cohere.autoconfigure.BedrockCohereEmbeddingAutoConfiguration;
import org.springframework.ai.model.bedrock.converse.autoconfigure.BedrockConverseProxyChatAutoConfiguration;
import org.springframework.ai.model.bedrock.titan.autoconfigure.BedrockTitanEmbeddingAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
        BedrockCohereEmbeddingAutoConfiguration.class,
        BedrockTitanEmbeddingAutoConfiguration.class,
        BedrockConverseProxyChatAutoConfiguration.class
})
@ComponentScan(basePackages = {"be.nicholasmeyers.meyersai.chat.adpater", "be.nicholasmeyers.meyersai.security"})
public class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
