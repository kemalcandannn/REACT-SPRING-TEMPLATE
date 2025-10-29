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
    const { jwtToken, sessionUser } = useAuthentication(); // JWT token varsa kullanıcı giriş yapmış demektir

    return (
        <Router>
            {/* Dil değiştirici her sayfada gözüksün */}
            <LanguageSwitcher />

            <Routes>
                <Route
                    path={NAVIGATE_PATHS.LOGIN}
                    element={jwtToken != null ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <Login />}
                />

                <Route
                    path={NAVIGATE_PATHS.SIGN_UP}
                    element={jwtToken != null ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <SignUp />}
                />

                <Route
                    path={NAVIGATE_PATHS.FORGOT_PASSWORD}
                    element={jwtToken != null ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <ForgotPassword />}
                />

                <Route
                    path={NAVIGATE_PATHS.CHANGE_PASSWORD}
                    element={jwtToken == null ? <Navigate to={NAVIGATE_PATHS.LOGIN} /> : <ChangePassword />}
                />

                <Route
                    path={NAVIGATE_PATHS.DASHBOARD}
                    element={jwtToken == null ? <Navigate to={NAVIGATE_PATHS.LOGIN} /> :
                        sessionUser?.passwordValidUntil != null &&
                            sessionUser?.passwordValidUntil < new Date() ? <Navigate to={NAVIGATE_PATHS.CHANGE_PASSWORD} /> : <Dashboard />}
                />


                <Route path="*" element={<Navigate to={NAVIGATE_PATHS.LOGIN} />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
