package be.nicholasmeyers.meyersai.chat.adpater.config;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpClientConfig {

    @Bean(name = "bank-server")
    public McpSyncClient bankServerMcpClient() {

        HttpClientSseClientTransport transport = HttpClientSseClientTransport
                .builder("http://192.168.10.201:8080")
                .build();

        return McpClient.sync(transport)
                .clientInfo(new McpSchema.Implementation("bank-client", "1.0.0"))
                .build();
    }

}
