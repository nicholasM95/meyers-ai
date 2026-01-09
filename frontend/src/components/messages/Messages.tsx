import {useEffect, useRef} from "react";
import AiIsTypingMessage from "../ai-is-typing-message";
import ChatMessage from "../chat-message";
import type {Message} from "../../models/Message.ts";

type MessagesProps = {
    messages: Message[];
    isAiTyping: boolean;
}

function Messages({messages, isAiTyping}: MessagesProps) {

    const messagesEndRef = useRef<HTMLDivElement | null>(null);


    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({behavior: 'smooth'});
    };

    useEffect(() => {
        scrollToBottom();
    }, [messages]);


    return (
        <div
            className="glass-morphism flex-1 overflow-y-auto p-6 space-y-4 scrollbar-thin scrollbar-thumb-emerald-500/50 scrollbar-track-transparent">
            {messages.map((message) => (
                <ChatMessage message={message}></ChatMessage>
            ))}

            {isAiTyping && (
                <AiIsTypingMessage></AiIsTypingMessage>
            )}

            <div ref={messagesEndRef}/>
        </div>
    )
}

export default Messages;

