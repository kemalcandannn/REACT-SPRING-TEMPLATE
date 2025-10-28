import React from "react";
import { AuthenticationProvider } from "./contexts/authentication/AuthenticationContext";
import { LanguageProvider } from "./contexts/language/LanguageContext";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import AppRouter from "./AppRouter";

const App: React.FC = () => (
  <AuthenticationProvider>
    <LanguageProvider>
      <>
        <LanguageSwitcher />
        <AppRouter />
      </>
    </LanguageProvider>
  </AuthenticationProvider>
);

export default App;
