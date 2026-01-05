package be.nicholasmeyers.meyersai.chat.domain;

public class ChatMessageFactory {

    private ChatMessageFactory() {
    }

    public static ChatMessage createChatMessage(ChatMessageCreateRequest chatMessageCreateRequest) {
        ChatMessage chatMessage = new ChatMessage(chatMessageCreateRequest);
        chatMessage.validate();
        return chatMessage;
    }
}
