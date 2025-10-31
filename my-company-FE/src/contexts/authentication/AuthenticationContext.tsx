import React, { createContext, useState, useContext, type ReactNode, useEffect } from "react";
import type { User } from "./model/User";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { SERVICE_PATHS } from "../../constants/Paths";
import type { Parameter } from "./model/Parameter";
import { LANGUAGES, type LANGUAGE } from "../../constants/Enumerations";

interface AuthenticationContextType {
    jwtToken: string | null;
    fillToken: (token: string) => void;
    clearToken: () => void;
    sessionUser: User | null;
    initSessionUser: () => void;
    language: LANGUAGE;
    setLanguage: (lang: LANGUAGE) => void;
    parameters: Parameter[];
}

const AuthenticationContext = createContext<AuthenticationContextType | undefined>(undefined);

export const AuthenticationProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [jwtToken, setJwtToken] = useState<string | null>(sessionStorage.getItem("jwtToken"));
    const [sessionUser, setSessionUser] = useState<User | null>(null);
    const [language, _setLanguage] = useState<LANGUAGE>("tr");
    const [parameters, setParameters] = useState<Parameter[]>([]);

    useEffect(() => {
        const savedParameters = sessionStorage.getItem("parametersProviderItems");
        const savedSessionUser = sessionStorage.getItem("sessionUserProviderItems");
        if (savedSessionUser) {
            setSessionUser(JSON.parse(savedSessionUser));
            sessionStorage.removeItem("sessionUserProviderItems");
        }

        const savedLanguage = sessionStorage.getItem("languageProviderItem");
        if (savedLanguage) {
            _setLanguage(JSON.parse(savedLanguage));
            sessionStorage.removeItem("languageProviderItem");
        }

        if (savedParameters) {
            setParameters(JSON.parse(savedParameters));
            sessionStorage.removeItem("parametersProviderItems");
        }
    }, []);

    useEffect(() => {
        const handleBeforeUnload = () => {
            if (sessionUser) {
                sessionStorage.setItem("sessionUserProviderItems", JSON.stringify(sessionUser));
            }

            if (language) {
                sessionStorage.setItem("languageProviderItem", JSON.stringify(language));
            }

            if (parameters) {
                sessionStorage.setItem("parametersProviderItems", JSON.stringify(parameters));
            }
        }

        window.addEventListener("beforeunload", handleBeforeUnload);

        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        }
    }, [parameters, sessionUser]);

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

        const lang = response?.data?.data?.language;
        _setLanguage(LANGUAGES[lang as keyof typeof LANGUAGES] ?? LANGUAGES.EN);
    };

    const setLanguage = async (language: LANGUAGE) => {
        if (sessionStorage.getItem("jwtToken")) {
            try {
                await BaseApiAxios.put(`${SERVICE_PATHS.API_V1_USER_CHANGE_LANGUAGE}/${language}`);
            } catch (err) {
                console.error(err);
            }
        }
        _setLanguage(language);
    }

    const initParameters = async () => {
        const response = await BaseApiAxios.get(SERVICE_PATHS.API_V1_PARAMETER_FIND_ALL_FROM_CACHE);
        setParameters(response?.data?.data);
    }

    return (
        <AuthenticationContext.Provider value={{ jwtToken, fillToken, clearToken, sessionUser, initSessionUser, language, setLanguage, parameters }}>
            {children}
        </AuthenticationContext.Provider>
    );
};

export const useAuthentication = () => {
    const context = useContext(AuthenticationContext);
    if (!context) throw new Error("useAuthentication must be used within AuthenticationProvider");
    return context;
};