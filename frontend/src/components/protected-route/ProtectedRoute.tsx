import {useAuth} from "react-oidc-context";
import {type ReactNode} from "react";

interface ProtectedRouteProps {
    children: ReactNode;
}

export default function ProtectedRoute({children}: ProtectedRouteProps) {

    const auth = useAuth();

    if (auth.isLoading) {
        return <div className="flex justify-center items-center min-h-screen">
            Loading...
        </div>;
    }

    if (!auth.isAuthenticated) {
        auth.signinRedirect().then(result => console.log(result));
        return <div className="flex justify-center items-center min-h-screen">
            Redirecting to login...
        </div>;
    }

    return children;
}