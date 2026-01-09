import './ChatPage.css';

import Button from "../../components/button";
import Textarea from "../../components/textarea";
import ChatHeader from "../../components/chat-header";
import Messages from "../../components/messages";
import BackgroundGrid from "../../components/backgound-grid";
import type {Message, Role} from "../../models/Message.ts";
import React, {useRef, useState} from "react";
import {sendChatMessage} from "../../services/ChatService.ts";

function ChatPage() {

    const [isAiTyping, setIsAiTyping] = useState(false);
    const [input, setInput] = useState('');
    const streamingMessageRef = useRef<Message | null>(null);

    const [messages, setMessages] = useState<Message[]>([
        {
            id: 1,
            role: 'assistant',
            content: 'System initialized. How can I assist you today?',
            timestamp: new Date()
        }
    ]);

    const handleSend = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        sendMessageToBackend().then(r => console.log('Sent message to backend:', r));
    }

    const onEnterInput = () => {
        sendMessageToBackend().then(r => console.log('Sent message to backend:', r));
    }

    const onInputChange = (input: string) => {
        setInput(input);
    }

    function createMessage(id: number, role: Role, content: string): Message {
        return {
            id: id,
            role,
            content,
            timestamp: new Date()
        };
    }

    async function sendMessageToBackend() {
        if (!input.trim() || isAiTyping) return;

        const messageContent = input.trim();
        setInput('');
        setIsAiTyping(true);

        const userMessage = createMessage(Date.now(), 'user', messageContent);
        setMessages(prev => [...prev, userMessage]);

        const aiMessage = createMessage(Date.now() + 1, 'assistant', '');
        streamingMessageRef.current = aiMessage;
        setMessages(prev => [...prev, aiMessage]);

        await sendChatMessage({
            message: messageContent,
            onChunk: (chunk: string) => {
                if (streamingMessageRef.current) {
                    streamingMessageRef.current.content += chunk;

                    setMessages(prev =>
                        prev.map(msg =>
                            msg.id === streamingMessageRef.current?.id
                                ? { ...msg, content: streamingMessageRef.current.content }
                                : msg
                        )
                    );
                }
            },
            onComplete: () => {
                setIsAiTyping(false);
                streamingMessageRef.current = null;
            },
            onError: () => {
                setIsAiTyping(false);

                if (streamingMessageRef.current) {
                    setMessages(prev =>
                        prev.map(msg =>
                            msg.id === streamingMessageRef.current?.id
                                ? { ...msg, content: 'Error: Failed to get response from AI. Please try again.' }
                                : msg
                        )
                    );
                }
                streamingMessageRef.current = null;
            }
        });
    }


    return (
        <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-emerald-950 flex items-center justify-center p-4 font-mono">
            <BackgroundGrid></BackgroundGrid>

            <div className="w-full max-w-4xl h-[85vh] flex flex-col relative z-10">
                <ChatHeader></ChatHeader>


                <Messages isAiTyping={false} messages={messages}></Messages>


                <div className="glass-morphism rounded-b-2xl p-4 border-t border-emerald-500/30">
                    <form onSubmit={handleSend} className="flex gap-3 py-2 items-stretch">
                        <Textarea onInputChange={onInputChange} onEnter={onEnterInput}></Textarea>
                        <Button isDisabled={isAiTyping || !input.trim()}/>
                    </form>
                    <p className="text-xs text-emerald-500/40 mt- text-center">
                        Press Enter to send • Shift+Enter for new line
                    </p>
                </div>
            </div>
        </div>
    );
}

export default ChatPage;
