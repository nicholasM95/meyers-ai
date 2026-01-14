package be.nicholasmeyers.meyersai.chat.adpater.config;

import io.spiffe.exception.JwtSvidException;
import io.spiffe.exception.SocketEndpointAddressException;
import io.spiffe.svid.jwtsvid.JwtSvid;
import io.spiffe.workloadapi.DefaultWorkloadApiClient;
import io.spiffe.workloadapi.WorkloadApiClient;
import org.springframework.ai.bedrock.converse.BedrockChatOptions;
import org.springframework.ai.bedrock.converse.BedrockProxyChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleWithWebIdentityRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleWithWebIdentityResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;

@Profile("!local")
@Configuration
public class BedrockChatConnector {

    private final Region region;
    private final StsClient stsClient;

    static {
        System.setProperty("io.netty.transport.noNative", "true");
        System.setProperty("io.grpc.netty.shaded.io.netty.transport.noNative", "true");
    }

    public BedrockChatConnector() {
        this.region = Region.EU_WEST_1;

        stsClient = StsClient.builder()
                .region(region)
                .credentialsProvider(AnonymousCredentialsProvider.create())
                .build();
    }


    @Bean
    public BedrockRuntimeClient bedrockClient() {
        return BedrockRuntimeClient.builder()
                .region(region)
                .credentialsProvider(createCredentialsProvider())
                .build();
    }

    @Bean
    @Primary
    public ChatModel bedrockProxyChatModel(BedrockRuntimeClient bedrockClient) {
        BedrockChatOptions options = BedrockChatOptions.builder()
                .model("amazon.titan-text-lite-v1")
                .build();

        return BedrockProxyChatModel.builder()
                .bedrockRuntimeClient(bedrockClient)
                .defaultOptions(options)
                .build();
    }

    private AwsCredentialsProvider createCredentialsProvider() {
        AwsSessionCredentials sessionCredentials = createAwsSessionCredentials();
        return () -> sessionCredentials;
    }

    private AwsSessionCredentials createAwsSessionCredentials() {
        String roleArn = "arn:aws:iam::896918338968:role/MeyersAIPolicy";
        String token = getSvid();

        AssumeRoleWithWebIdentityRequest request = AssumeRoleWithWebIdentityRequest.builder()
                .roleArn(roleArn)
                .roleSessionName(UUID.randomUUID().toString())
                .webIdentityToken(token)
                .durationSeconds(900)
                .build();

        AssumeRoleWithWebIdentityResponse response = stsClient.assumeRoleWithWebIdentity(request);

        return AwsSessionCredentials.create(
                response.credentials().accessKeyId(),
                response.credentials().secretAccessKey(),
                response.credentials().sessionToken()
        );
    }


    private String getSvid() {
        System.setProperty("io.netty.transport.noNative", "true");
        System.setProperty("io.grpc.netty.shaded.io.netty.transport.noNative", "true");

        try {
            DefaultWorkloadApiClient.ClientOptions options = DefaultWorkloadApiClient.ClientOptions.builder()
                    .executorService(Executors.newCachedThreadPool())
                    .build();

            WorkloadApiClient workloadApiClient = DefaultWorkloadApiClient.newClient(options);
            JwtSvid svid = workloadApiClient.fetchJwtSvid("sts.amazonaws.com");
            workloadApiClient.close();
            return svid.getToken();
        } catch (JwtSvidException | SocketEndpointAddressException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}