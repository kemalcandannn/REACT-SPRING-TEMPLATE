import React from "react";
import { AuthenticationProvider } from "./contexts/authentication/AuthenticationContext";
import { LanguageProvider } from "./contexts/language/LanguageContext";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import AppRouter from "./AppRouter";
import { ParametersProvider } from "./contexts/parameters/ParametersContext";

const App: React.FC = () => (
  <LanguageProvider>
    <AuthenticationProvider>
      <ParametersProvider>
        <>
          <LanguageSwitcher />
          <AppRouter />
        </>
      </ParametersProvider>
    </AuthenticationProvider>
  </LanguageProvider>
);

export default App;
