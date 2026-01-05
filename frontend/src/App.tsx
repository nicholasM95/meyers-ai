import './App.css'
import {Route, Routes} from "react-router-dom";
import ChatPage from "./pages/chat";

function App() {
    return (
        <Routes>
            <Route path="/" element={<ChatPage />} />
        </Routes>
    );
}
export default App
