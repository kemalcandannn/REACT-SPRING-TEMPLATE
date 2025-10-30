import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate, BrowserRouter } from "react-router-dom";
import { useAuthentication } from "./contexts/authentication/AuthenticationContext";
import { NAVIGATE_PATHS } from "./constants/Paths";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Logout from "./pages/Logout";
import ForgotPassword from "./pages/ForgotPassword";
import ChangePassword from "./pages/ChangePassword";
import VerifyAccount from "./pages/VerifyAccount";
import Dashboard from "./pages/Dashboard";
import LanguageSwitcher from "./contexts/language/LanguageSwitcher";
import ResetPassword from "./pages/ResetPassword";

const AppRouter: React.FC = () => {
    const { jwtToken } = useAuthentication(); // JWT token varsa kullanıcı giriş yapmış demektir

    return (
        <Router>
            {jwtToken == null && <LanguageSwitcher />}

            <Routes>
                <Route
                    path={NAVIGATE_PATHS.LOGIN}
                    element={jwtToken != null ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <Login />}
                />

                <Route
                    path={NAVIGATE_PATHS.REGISTER}
                    element={jwtToken != null ? <Navigate to={NAVIGATE_PATHS.DASHBOARD} /> : <Register />}
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
                    path={NAVIGATE_PATHS.RESET_PASSWORD}
                    element={<ResetPassword />}
                />

                <Route
                    path={NAVIGATE_PATHS.DASHBOARD}
                    element={jwtToken == null ? <Navigate to={NAVIGATE_PATHS.LOGIN} /> : <Dashboard />}
                />

                <Route
                    path={NAVIGATE_PATHS.VERIFY_ACCOUNT}
                    element={<VerifyAccount />}
                />

                <Route
                    path={NAVIGATE_PATHS.LOGOUT}
                    element={<Logout />}
                />


                <Route path="*" element={<Navigate to={NAVIGATE_PATHS.LOGIN} />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
