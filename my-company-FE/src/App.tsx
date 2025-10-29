import React from "react";
import { AuthenticationProvider } from "./contexts/authentication/AuthenticationContext";
import { LanguageProvider } from "./contexts/language/LanguageContext";
import AppRouter from "./AppRouter";

const App: React.FC = () => (
  <LanguageProvider>
    <AuthenticationProvider>
      <AppRouter />
    </AuthenticationProvider>
  </LanguageProvider>
);

export default App;
