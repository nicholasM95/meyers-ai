package be.nicholasmeyers.meyersai.chat.adpater.controller;

import be.nicholasmeyers.meyersai.chat.domain.ChatMessageCreateRequest;
import be.nicholasmeyers.meyersai.chat.usecase.SendChatMessageUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final SendChatMessageUseCase sendChatMessageUseCase;

    public ChatController(SendChatMessageUseCase sendChatMessageUseCase) {
        this.sendChatMessageUseCase = sendChatMessageUseCase;
    }

    @PostMapping(value = "/message", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chatMessage(@RequestBody CreateChatMessageRequestResource createChatMessageRequestResource) {
        SseEmitter emitter = new SseEmitter(300000L);

        CompletableFuture.runAsync(() -> {
            sendChatMessageUseCase.sendChatMessage(
                    new ChatMessageCreateRequest(createChatMessageRequestResource.message()),
                    chunk -> {
                        try {
                            emitter.send(chunk);
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
