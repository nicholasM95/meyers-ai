function AiIsTypingMessage() {


    return (
        <div className="message-appear flex justify-start">
            <div className="bg-slate-800/60 border border-slate-700/50 rounded-lg p-4">
                <div className="flex items-center gap-2 mb-2">
                    <span className="text-xs font-bold text-cyan-400">[AI]</span>
                    <span className="text-xs text-slate-500">processing...</span>
                </div>
                <div className="typing-indicator flex gap-1 items-center">
                    <span className="w-2 h-2 bg-emerald-500 rounded-full"></span>
                    <span className="w-2 h-2 bg-emerald-500 rounded-full"></span>
                    <span className="w-2 h-2 bg-emerald-500 rounded-full"></span>
                </div>
            </div>
        </div>
    )
}

export default AiIsTypingMessage;

