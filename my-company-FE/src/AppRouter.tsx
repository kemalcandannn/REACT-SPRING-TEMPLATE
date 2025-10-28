import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useAuthentication } from "./contexts/authentication/AuthenticationContext";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import Login from "./pages/authorization/Login";
import Dashboard from "./pages/Dashboard";
import SignUp from "./pages/authorization/SignUp";

const AppRouter: React.FC = () => {
    const { token } = useAuthentication(); // JWT token varsa kullanıcı giriş yapmış demektir

    return (
        <Router>
            {/* Dil değiştirici her sayfada gözüksün */}
            <LanguageSwitcher />

            <Routes>
                <Route
                    path="/login"
                    element={token ? <Navigate to="/dashboard" /> : <Login />}
                />
                <Route
                    path="/signUp"
                    element={token ? <Navigate to="/dashboard" /> : <SignUp />}
                />
                <Route
                    path="/dashboard"
                    element={token ? <Dashboard /> : <Navigate to="/login" />}
                />

                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
