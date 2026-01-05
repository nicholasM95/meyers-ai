import type {Message} from "../../models/Message.ts";

type MessageProps = {
    message: Message;
}

function ChatMessage({message}: MessageProps) {


    return (
        <div
            key={message.id}
            className={`message-appear flex ${message.role === 'user' ? 'justify-end' : 'justify-start'}`}
        >
            <div
                className={`max-w-[80%] rounded-lg p-4 ${
                    message.role === 'user'
                        ? 'bg-emerald-500/20 border border-emerald-500/40 text-emerald-100'
                        : 'bg-slate-800/60 border border-slate-700/50 text-slate-200'
                }`}
            >
                <div className="flex items-start gap-2 mb-2">
                  <span className={`text-xs font-bold ${
                      message.role === 'user' ? 'text-emerald-400' : 'text-cyan-400'
                  }`}>
                    {message.role === 'user' ? '[USER]' : '[AI]'}
                  </span>
                    <span className="text-xs text-slate-500">
                    {message.timestamp.toLocaleTimeString('nl-NL', {
                        hour: '2-digit',
                        minute: '2-digit',
                        second: '2-digit'
                    })}
                  </span>
                </div>
                <p className="text-sm leading-relaxed whitespace-pre-wrap">
                    {message.content}
                </p>
            </div>
        </div>
    )
}

export default ChatMessage;

