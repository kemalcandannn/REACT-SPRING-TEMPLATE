import React from "react";
import { Button, Stack, Box } from "@mui/material";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import { LANGUAGES } from "../constants/Enumerations";

const LanguageSwitcher: React.FC = () => {
    const { setLanguage } = useAuthentication();

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
                {Object.values(LANGUAGES).map((lang) => (
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
