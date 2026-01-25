
export const getAuthToken = (): string => {
    const userData = sessionStorage.getItem('oidc.user:https://keycloak.nicholasmeyers.be/realms/nicholasmeyers-public:meyers-ai-frontend');
    if (typeof userData === "string") {
        const data = JSON.parse(userData);
        return data.access_token;
    }
    return '';
};