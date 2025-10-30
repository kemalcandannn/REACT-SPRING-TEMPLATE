import React, { createContext, useContext, useEffect, useState, type ReactNode } from "react";
import type { Language } from "./LanguageSwitcher";

interface LanguageContextType {
    language: Language;
    setLanguage: (lang: Language) => void;
}

// Context oluştur
const LanguageContext = createContext<LanguageContextType | undefined>(undefined);

// Provider bileşeni
export const LanguageProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
    const [language, setLanguage] = useState<Language>("tr");

    useEffect(() => {
        const savedLangugage = sessionStorage.getItem("languageProviderItem");
        if (savedLangugage) {
            setLanguage(JSON.parse(savedLangugage));
            sessionStorage.removeItem("languageProviderItem");
        }

    }, []);

    useEffect(() => {
        const handleBeforeUnload = () => {
            if (language) {
                sessionStorage.setItem("languageProviderItem", JSON.stringify(language));
            }
        }

        window.addEventListener("beforeunload", handleBeforeUnload);

        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        }
    }, [language]);

    return (
        <LanguageContext.Provider value={{ language, setLanguage }}>
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
