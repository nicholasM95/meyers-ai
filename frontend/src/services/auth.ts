
export const getAuthToken = (): string => {
    const userData = sessionStorage.getItem('oidc.user:https://keycloak.nicholasmeyers.be/realms/meyers-prive:meyers-ai-frontend');
    if (typeof userData === "string") {
        const data = JSON.parse(userData);
        return data.access_token;
    }
    return '';
};