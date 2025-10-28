import React, { createContext, useState, useContext, useEffect, type ReactNode } from "react";
import type { Parameter } from "./model/Parameter";
import BaseApiAxios from "../../helpers/BaseApiAxios";

interface ParametersContextType {
    parameters: Parameter[];
    initParameters: () => {};
}

const ParametersContext = createContext<ParametersContextType | undefined>(undefined);

export const ParametersProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [parameters, setParameters] = useState<Parameter[]>([]);

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

    const initParameters = async () => {
        const response = await BaseApiAxios.get("/api/v1/parameter/get-parameters");
        setParameters(response?.data?.data);
    }

    return (
        <ParametersContext.Provider value={{ parameters, initParameters }}>
            {children}
        </ParametersContext.Provider>
    );
};

export const useParameters = () => {
    const context = useContext(ParametersContext);
    if (!context) throw new Error("useParameters must be used within ParametersProvider");
    return context;
};
