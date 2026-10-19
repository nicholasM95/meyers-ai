package be.nicholasmeyers.meyersai.chat.adapter.openrouter.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Profile("!local")
@ConditionalOnProperty(name = "chat.provider", havingValue = "openrouter")
@Configuration
public class OpenRouterChatConnector {

    private static final Logger log = LoggerFactory.getLogger(OpenRouterChatConnector.class);

    private final String baseUrl;
    private final String apiKey;
    private final String model;
    private final ToolCallbackProvider toolCallbackProvider;

    public OpenRouterChatConnector(
            @Value("${chat.openrouter.base-url:https://openrouter.ai/api}") String baseUrl,
            @Value("${chat.openrouter.api-key}") String apiKey,
            @Value("${chat.openrouter.model:openai/gpt-oss-20b:free}") String model,
            ToolCallbackProvider toolCallbackProvider) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.model = model;
        this.toolCallbackProvider = toolCallbackProvider;
    }

    @Bean
    public ChatModel openRouterChatModel() {
        ToolCallback[] tools = toolCallbackProvider.getToolCallbacks();
        log.info("Available tools: {}",
                Arrays.stream(tools).map(ToolCallback::getToolDefinition).map(ToolDefinition::name).toList());

        OpenAiApi openAiApi = OpenAiApi.builder()
                .baseUrl(baseUrl)
                .completionsPath("/v1/chat/completions")
                .apiKey(apiKey)
                .build();

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model(model)
                .temperature(0.0)
                .toolCallbacks(tools)
                .build();

        return OpenAiChatModel.builder()
                .openAiApi(openAiApi)
                .defaultOptions(options)
                .build();
    }
}
