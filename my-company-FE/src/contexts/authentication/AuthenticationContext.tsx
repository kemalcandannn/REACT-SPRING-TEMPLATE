import React, { createContext, useState, useContext, type ReactNode } from "react";

interface AuthenticationContextType {
    token: string | null;
    login: (token: string) => void;
    logout: () => void;
}

const AuthenticationContext = createContext<AuthenticationContextType | undefined>(undefined);

export const AuthenticationProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [token, setToken] = useState<string | null>(sessionStorage.getItem("token"));

    const login = (newToken: string) => {
        sessionStorage.setItem("token", newToken);
        setToken(newToken);
    };

    const logout = () => {
        sessionStorage.removeItem("token");
        setToken(null);
    };

    return (
        <AuthenticationContext.Provider value={{ token, login, logout }}>
            {children}
        </AuthenticationContext.Provider>
    );
};

export const useAuthentication = () => {
    const context = useContext(AuthenticationContext);
    if (!context) throw new Error("useAuth must be used within AuthProvider");
    return context;
};