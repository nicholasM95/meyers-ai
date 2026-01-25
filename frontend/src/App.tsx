import './App.css'
import {Route, Routes} from "react-router-dom";
import ChatPage from "./pages/chat";
import {AuthProvider} from "react-oidc-context";
import ProtectedRoute from "./components/protected-route";

const oidcConfig = {
    authority: 'https://keycloak.nicholasmeyers.be/realms/nicholasmeyers-public',
    client_id: "meyers-ai-frontend",
    redirect_uri: 'https://ai.nicholasmeyers.be',
    response_type: "code",
    scope: "openid profile email",
};


function App() {
    return (
        <AuthProvider {...oidcConfig}>
            <ProtectedRoute>
                <Routes>
                    <Route path="/" element={<ChatPage />} />
                </Routes>
            </ProtectedRoute>
        </AuthProvider>
    );
}
export default App
