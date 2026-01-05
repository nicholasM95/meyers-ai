function ChatHeader() {


    return (
        <div className="glass-morphism rounded-t-2xl p-4 border-b border-emerald-500/30">
            <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                    <div className="w-3 h-3 rounded-full bg-emerald-500 animate-pulse" style={{ animation: 'pulse-glow 2s infinite' }}></div>
                    <h1 className="text-emerald-400 text-xl font-bold tracking-wider">
                        &gt; Meyers AI
                    </h1>
                </div>
                <div className="flex gap-2 text-xs text-emerald-500/60">
                    <span>{new Date().toLocaleDateString('nl-NL')}</span>
                    <span>•</span>
                    <span>{new Date().toLocaleTimeString('nl-NL', { hour: '2-digit', minute: '2-digit' })}</span>
                </div>
            </div>
        </div>
    )
}

export default ChatHeader;

