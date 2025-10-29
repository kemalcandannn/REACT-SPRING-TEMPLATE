import { useState } from "react";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { useNavigate, useSearchParams } from "react-router-dom";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import "./style/Authorization.css";
import { NAVIGATE_PATHS } from "../../constants/Paths";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";

const ResetPassword: React.FC = () => {
    const { getLabel } = useLanguage();
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token"); // URL'den token alıyoruz

    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setError("");
        setSuccess("");

        if (newPassword !== confirmPassword) {
            setError(getLabel("newPasswordDoesNotConfirm"));
            setLoading(false);
            return;
        }

        try {
            await BaseApiAxios.post("api/v1/authentication/reset-password", {
                token,
                newPassword,
                confirmPassword
            });

            setSuccess(getLabel("passwordChangedSuccessfully"));
            setNewPassword("");
            setConfirmPassword("");

            setTimeout(() => navigate(NAVIGATE_PATHS.LOGIN), 1600);
        } catch (err: any) {
            setError(handleApiError(err));
        }

        setLoading(false);
    };

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("resetPasswordTitle")}</h2>

                <form onSubmit={handleSubmit}>
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

                <p>
                    <a href="#"
                        onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                            e.preventDefault();
                            navigate(NAVIGATE_PATHS.LOGIN);
                        }}>
                        {getLabel("backToLogin")}
                    </a>
                </p>
            </div>
        </div>
    );
};

export default ResetPassword;
