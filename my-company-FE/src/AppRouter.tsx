import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { useAuthentication } from "./contexts/authentication/AuthenticationContext";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import Login from "./pages/authorization/Login";
import Dashboard from "./pages/Dashboard";
import SignUp from "./pages/authorization/SignUp";
import ForgotPassword from "./pages/authorization/ForgotPassword";
import ChangePassword from "./pages/authorization/ChangePassword";
import { NAVIGATE_PATHS } from "./constants/Paths";

const AppRouter: React.FC = () => {
    const { token } = useAuthentication(); // JWT token varsa kullanıcı giriş yapmış demektir

    return (
        <Router>
            {/* Dil değiştirici her sayfada gözüksün */}
            <LanguageSwitcher />

            <Routes>
                <Route
                    path={NAVIGATE_PATHS.LOGIN}
                    element={token ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <Login />}
                />
                <Route
                    path={NAVIGATE_PATHS.SIGN_UP}
                    element={token ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <SignUp />}
                />
                <Route
                    path={NAVIGATE_PATHS.FORGOT_PASSWORD}
                    element={token ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <ForgotPassword />} />

                <Route
                    path={NAVIGATE_PATHS.CHANGE_PASSWORD}
                    element={<ChangePassword />} />

                <Route
                    path={NAVIGATE_PATHS.DASHBOARD}
                    element={token ? <Dashboard /> : <Navigate to={NAVIGATE_PATHS.LOGIN} />}
                />


                <Route path="*" element={<Navigate to={NAVIGATE_PATHS.LOGIN} />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
