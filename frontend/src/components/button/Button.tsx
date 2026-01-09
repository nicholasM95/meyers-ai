
type ButtonProps = {
    isDisabled?: boolean;
}

function Button({ isDisabled }: ButtonProps) {

   return (
       <button
           type="submit"
           disabled={isDisabled}
           className="padding-3 bg-emerald-500 hover:bg-emerald-400 disabled:bg-slate-700 disabled:cursor-not-allowed text-slate-950 font-bold px-6 self-stretch rounded-lg transition-all duration-200 hover:shadow-lg hover:shadow-emerald-500/50 disabled:shadow-none flex items-center gap-2 group"
       >
           <span>SEND</span>
           <svg
               className="w-4 h-4 group-hover:translate-x-1 transition-transform"
               fill="none"
               stroke="currentColor"
               viewBox="0 0 24 24"
           >
               <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7l5 5m0 0l-5 5m5-5H6" />
           </svg>
       </button>
   )
}

export default Button;

