import { useState } from "react";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useAuthentication } from "../../contexts/authentication/AuthenticationContext";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";
import { useNavigate } from "react-router-dom";
import "./style/Authorization.css";
import { NAVIGATE_PATHS, SERVICE_PATHS } from "../../constants/Paths";

const ChangePassword: React.FC = () => {
    const { getLabel } = useLanguage();
    const { logout } = useAuthentication();
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();

    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");
        setSuccess("");
        setLoading(true);

        try {
            await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_CHANGE_PASSWORD, {
                oldPassword,
                newPassword,
                confirmPassword
            });

            setSuccess(getLabel("passwordChangedSuccessfully"));
            setOldPassword("");
            setNewPassword("");
            setConfirmPassword("");
        } catch (err: any) {
            setError(handleApiError(err));
        }

        setLoading(false);
    };

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("changePasswordTitle")}</h2>

                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("oldPassword")}
                            value={oldPassword}
                            onChange={(e) => setOldPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("newPassword")}
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <input
                            type="password"
                            placeholder={getLabel("confirmNewPassword")}
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <button type="submit" disabled={loading}>
                            {loading ? getLabel("loading") : getLabel("changePassword")}
                        </button>
                    </div>

                    {error && <div className="error-text">{error}</div>}
                    {success && <div className="success-text">{success}</div>}
                </form>

                <p style={{ marginTop: "20px" }}>
                    <a
                        onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                            e.preventDefault();
                            logout();
                            navigate(NAVIGATE_PATHS.LOGIN);
                        }}
                        href="#"
                    >
                        {getLabel("logout")}
                    </a>
                </p>
            </div>
        </div>
    );
};

export default ChangePassword;
