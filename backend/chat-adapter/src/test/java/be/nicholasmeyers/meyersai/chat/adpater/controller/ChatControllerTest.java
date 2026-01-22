package be.nicholasmeyers.meyersai.chat.adpater.controller;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessageCreateRequest;
import be.nicholasmeyers.meyersai.chat.usecase.SendChatMessageUseCase;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
@ContextConfiguration(classes = ChatTestConfig.class)
public class ChatControllerTest {

    @MockitoBean
    private SendChatMessageUseCase sendChatMessageUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Nested
    class ChatMessage {
        @Test
        void givenChatMessage_whenChatMessage_thenVerifyUseCaseIsCalled() throws Exception {
            // Given
            String request = """
                    {
                        "message": "Hello world"
                    }
                    """;

            // When & Then
            mockMvc.perform(post("/chat/message")
                            .accept(MediaType.TEXT_EVENT_STREAM_VALUE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request))
                    .andExpect(status().isOk());

            verify(sendChatMessageUseCase).sendChatMessage(eq(new ChatMessageCreateRequest("Hello world")), any(), any(), any());
        }
    }
}
