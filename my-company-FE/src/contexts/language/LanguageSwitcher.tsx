import React from "react";
import { useLanguage } from "./LanguageContext";
import { Button, Stack, Box } from "@mui/material";

//export const LANGUGAGES = ["tr", "en", "de", "ru", "ar"] as const;
export const LANGUGAGES = ["tr", "en"] as const;
export type Language = typeof LANGUGAGES[number];

const LanguageSwitcher: React.FC = () => {
    const { setLanguage } = useLanguage();

    return (
        <Box
            sx={{
                position: "fixed",
                top: 16,
                right: 16,
                zIndex: 1000,
            }}
        >
            <Stack direction="row" spacing={1}>
                {LANGUGAGES.map((lang) => (
                    <Button
                        key={lang}
                        variant="outlined"
                        size="small"
                        onClick={() => setLanguage(lang)}
                    >
                        {lang.toUpperCase()}
                    </Button>
                ))}
            </Stack>
        </Box>
    );
};

export default LanguageSwitcher;
