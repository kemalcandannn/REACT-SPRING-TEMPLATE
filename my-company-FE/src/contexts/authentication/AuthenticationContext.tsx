import React, { createContext, useState, useContext, type ReactNode, useEffect } from "react";
import type { User } from "./model/User";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { SERVICE_PATHS } from "../../constants/Paths";
import type { Parameters } from "./model/Parameters";

interface AuthenticationContextType {
    jwtToken: string | null;
    fillToken: (token: string) => void;
    clearToken: () => void;
    sessionUser: User | null;
    parameters: Parameters[];
}

const AuthenticationContext = createContext<AuthenticationContextType | undefined>(undefined);

export const AuthenticationProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [jwtToken, setJwtToken] = useState<string | null>(sessionStorage.getItem("jwtToken"));
    const [sessionUser, setSessionUser] = useState<User | null>(null);
    const [parameters, setParameters] = useState<Parameters[]>([]);

    useEffect(() => {
        const savedParameters = sessionStorage.getItem("parametersProviderItems");
        if (savedParameters) {
            setParameters(JSON.parse(savedParameters));
            sessionStorage.removeItem("parametersProviderItems");
        }
    }, []);

    useEffect(() => {
        const handleBeforeUnload = () => {
            if (parameters) {
                sessionStorage.setItem("parametersProviderItems", JSON.stringify(parameters));
            }
        }

        window.addEventListener("beforeunload", handleBeforeUnload);

        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        }
    }, [parameters]);

    const fillToken = (newToken: string) => {
        sessionStorage.setItem("jwtToken", newToken);
        setJwtToken(newToken);
        initSessionUser();
        initParameters();
    };

    const clearToken = () => {
        sessionStorage.removeItem("jwtToken");
        setJwtToken(null);
        setSessionUser(null);
        setParameters([]);
    };

    const initSessionUser = async () => {
        const response = await BaseApiAxios.get(SERVICE_PATHS.API_V1_AUTHENTICATION_USER);
        setSessionUser(response?.data?.data);
    };

    const initParameters = async () => {
        const response = await BaseApiAxios.get(SERVICE_PATHS.API_V1_PARAMETERS_FIND_ALL_FROM_CACHE);
        setParameters(response?.data?.data);
    }

    return (
        <AuthenticationContext.Provider value={{ jwtToken, fillToken, clearToken, sessionUser, parameters }}>
            {children}
        </AuthenticationContext.Provider>
    );
};

export const useAuthentication = () => {
    const context = useContext(AuthenticationContext);
    if (!context) throw new Error("useAuthentication must be used within AuthenticationProvider");
    return context;
};