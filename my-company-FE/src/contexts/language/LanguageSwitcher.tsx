import { LANGUGAGES } from "../../constants/Labels";
import { useLanguage } from "./LanguageContext";

const LanguageSwitcher: React.FC = () => {
    const { setLanguage } = useLanguage();

    return (
        <div style={{ position: "fixed", top: 0, right: 0 }}>
            {LANGUGAGES.map((lang) => (
                <button
                    key={lang}
                    onClick={() => setLanguage(lang)}
                >
                    {lang.toUpperCase()}
                </button>
            ))}
        </div>
    );
};

export default LanguageSwitcher