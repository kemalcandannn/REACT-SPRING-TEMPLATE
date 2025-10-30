import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './pages/Login';
import Register from './pages/Register';
import ForgotPassword from './pages/ForgotPassword';
import { LanguageProvider } from './contexts/language/LanguageContext';
import { AuthenticationProvider } from './contexts/authentication/AuthenticationContext';
import AppRouter from './AppRouter';

function App() {
  return (
    <LanguageProvider>
      <AuthenticationProvider>
        <AppRouter />
      </AuthenticationProvider>
    </LanguageProvider>
  );
}

export default App;
