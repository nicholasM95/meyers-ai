package be.nicholasmeyers.meyersai.chat.adapter.controller;

import be.nicholasmeyers.meyersai.chat.adapter.resource.CreateChatMessageRequest;
import be.nicholasmeyers.meyersai.chat.domain.ChatMessageCreateRequest;
import be.nicholasmeyers.meyersai.chat.usecase.SendChatMessageUseCase;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(origins = "*")
public class ChatController implements ChatApi {

    private final SendChatMessageUseCase sendChatMessageUseCase;

    public ChatController(SendChatMessageUseCase sendChatMessageUseCase) {
        this.sendChatMessageUseCase = sendChatMessageUseCase;
    }

    @Override
    public SseEmitter sendChatMessage(CreateChatMessageRequest createChatMessageRequest) {
        SseEmitter emitter = new SseEmitter(300000L);

        CompletableFuture.runAsync(() -> {
            sendChatMessageUseCase.sendChatMessage(
                    new ChatMessageCreateRequest(createChatMessageRequest.getMessage()),
                    chunk -> {
                        try {
                            emitter.send(SseEmitter.event()
                                    .data(chunk)
                                    .build());
                        } catch (Exception e) {
                            emitter.completeWithError(e);
                        }
                    },
                    emitter::complete,
                    emitter::completeWithError
            );
        });
        return emitter;
    }
}
