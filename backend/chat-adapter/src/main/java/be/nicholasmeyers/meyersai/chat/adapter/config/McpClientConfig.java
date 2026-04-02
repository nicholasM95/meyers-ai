package be.nicholasmeyers.meyersai.chat.adapter.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class McpClientConfig {

    /*@Bean(name = "bank-server")
    public McpSyncClient bankServerMcpClient() {

        HttpClientSseClientTransport transport = HttpClientSseClientTransport
                .builder("http://bank-mcp-server.bank-mcp-server.svc.cluster.local:8080")
                .build();

        return McpClient.sync(transport)
                .clientInfo(new McpSchema.Implementation("bank-client", "1.0.0"))
                .build();
    }

    @Bean
    public ToolCallbackProvider mcpToolCallbackProvider(@Qualifier("bank-server") McpSyncClient bankServerClient) {
        return SyncMcpToolCallbackProvider.builder()
                .mcpClients(bankServerClient)
                .build();
    }*/

}
