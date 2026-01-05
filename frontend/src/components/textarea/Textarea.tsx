import React, {useRef, useState} from "react";

type TextareaProps = {
    onInputChange: (input: string) => void;
    onEnter: () => void;
}

function Textarea({onInputChange, onEnter}: TextareaProps) {

    const inputRef = useRef(null);
    const [input, setInput] = useState('');

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        const value = e.target.value;
        setInput(value);
        onInputChange(value);
    };

    const handleEnter = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        e.preventDefault();
        setInput('');
        onEnter();
    }


   return (
       <div className="flex-1 relative">
           <div className="absolute left-3 top-3 text-emerald-500/50 text-sm pointer-events-none">
               &gt;_
           </div>
           <textarea
               id="message-input"
               ref={inputRef}
               value={input}
               onChange={(e) => handleChange(e)}
               onKeyDown={(e) => {
                   if (e.key === 'Enter' && !e.shiftKey) {
                       e.preventDefault();
                       handleEnter(e);
                   }
               }}
               placeholder="Type your message..."
               className="w-full h-full bg-slate-900/50 border border-emerald-500/30 rounded-lg px-10 py-3 text-sm text-emerald-100 placeholder-emerald-500/30 focus:outline-none focus:border-emerald-500 focus:ring-2 focus:ring-emerald-500/20 transition-all resize-none"
               rows={2}
           />
       </div>
   )
}

export default Textarea;

