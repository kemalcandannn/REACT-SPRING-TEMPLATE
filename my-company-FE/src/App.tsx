import React from "react";
import { AuthenticationProvider } from "./contexts/authentication/AuthenticationContext";
import { LanguageProvider } from "./contexts/language/LanguageContext";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import AppRouter from "./AppRouter";

const App: React.FC = () => (
  <LanguageProvider>
    <AuthenticationProvider>
      <>
        <LanguageSwitcher />
        <AppRouter />
      </>
    </AuthenticationProvider>
  </LanguageProvider>
);

export default App;
