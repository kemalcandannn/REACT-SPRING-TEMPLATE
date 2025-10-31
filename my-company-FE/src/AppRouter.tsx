import React, { type ReactNode } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import { NAVIGATE_PATHS } from "./constants/Paths";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Logout from "./pages/Logout";
import ForgotPassword from "./pages/ForgotPassword";
import ChangePassword from "./pages/ChangePassword";
import VerifyAccount from "./pages/VerifyAccount";
import Dashboard from "./pages/Dashboard";
import ResetPassword from "./pages/ResetPassword";
import SystemParameters from "./pages/SystemParameters";
import { useAuthentication } from "./contexts/authentication/AuthenticationContext";
import Layout from "./components/Layout";

interface PrivateRouteProps {
    children: ReactNode;
}


export const PrivateRoute: React.FC<PrivateRouteProps> = ({ children }) => {
    const { jwtToken, sessionUser } = useAuthentication();
    if (!jwtToken) return <Navigate to="/login" />;

    if (sessionUser?.passwordValidUntil && new Date(sessionUser.passwordValidUntil) < new Date()) {
        return <ChangePassword />;
    }

    return <Layout>{children}</Layout>; // tüm private sayfalara header/footer
};

interface PublicRouteProps {
    children: ReactNode;
}

const PublicRoute: React.FC<PublicRouteProps> = ({ children }) => {
    const { jwtToken } = useAuthentication();
    return jwtToken ? <Navigate to="/dashboard" /> : children;
};

const AppRouter: React.FC = () => {
    return (
        <Router>
            <Routes>
                {/* Public routes */}
                <Route
                    path={NAVIGATE_PATHS.LOGIN}
                    element={<PublicRoute><Login /></PublicRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.REGISTER}
                    element={<PublicRoute><Register /></PublicRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.FORGOT_PASSWORD}
                    element={<PublicRoute><ForgotPassword /></PublicRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.RESET_PASSWORD}
                    element={<PublicRoute><ResetPassword /></PublicRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.VERIFY_ACCOUNT}
                    element={<PublicRoute><VerifyAccount /></PublicRoute>}
                />

                {/* Private routes */}
                <Route
                    path={NAVIGATE_PATHS.DASHBOARD}
                    element={<PrivateRoute><Dashboard /></PrivateRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.CHANGE_PASSWORD}
                    element={<PrivateRoute><ChangePassword /></PrivateRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.SYSTEM_PARAMETERS}
                    element={<PrivateRoute><SystemParameters /></PrivateRoute>}
                />
                <Route
                    path={NAVIGATE_PATHS.LOGOUT}
                    element={<PrivateRoute><Logout /></PrivateRoute>}
                />

                {/* Catch-all */}
                <Route path="*" element={<Navigate to={NAVIGATE_PATHS.LOGIN} />} />
            </Routes>
        </Router>
    );
};

export default AppRouter;
