import React, { createContext, useContext, useState, type ReactNode } from "react";
import { labelsData, type Labels, type Language } from "../../constants/Labels";

type LabelKey = keyof Labels;

interface LanguageContextType {
    language: Language;
    setLanguage: (lang: Language) => void;
    getLabel: (key: LabelKey) => string;
}

// Context oluştur
const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

// Provider bileşeni
export const LanguageProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [language, setLanguage] = useState<Language>("tr");

    const getLabel = (key: LabelKey): string => {
        return (labelsData[language as Language][key]) ?? ""
    };

    return (
        <LanguageContext.Provider value={{ language, setLanguage, getLabel }}>
            {children}
        </LanguageContext.Provider>
    );
};

// Hook ile kolay kullanım
export const useLanguage = (): LanguageContextType => {
    const context = useContext(LanguageContext);
    if (!context) {
        throw new Error("useLanguage must be used within a LanguageProvider");
    }
    return context;
};
