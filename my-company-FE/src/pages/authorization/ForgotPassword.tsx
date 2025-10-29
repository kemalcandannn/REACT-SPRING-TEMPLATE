import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useLanguage } from "../../contexts/language/LanguageContext";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import "./style/Authorization.css";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";
import { NAVIGATE_PATHS, SERVICE_PATHS } from "../../constants/Paths";

const ForgotPassword: React.FC = () => {
    const { getLabel } = useLanguage();
    const { handleApiError } = useApiErrorHandler();
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [error, setError] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setMessage("");
        setError("");

        try {
            await BaseApiAxios.post(SERVICE_PATHS.API_V1_AUTHENTICATION_FORGOT_PASSWORD, {
                username: username
            });

            setMessage(getLabel("passwordResetEmailSent"));
        } catch (err: any) {
            setError(handleApiError(err));
        }

        setLoading(false);
    };

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("forgotPasswordTitle")}</h2>

                <form onSubmit={handleSubmit}>
                    <div>
                        <input
                            type="email"
                            placeholder={getLabel("email")}
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>

                    <div>
                        <button type="submit" disabled={loading}>
                            {loading ? getLabel("loading") : getLabel("resetPassword")}
                        </button>
                    </div>

                    {message && <div className="success-text">{message}</div>}
                    {error && <div className="error-text">{error}</div>}
                </form>

                <p>
                    <a
                        href="#"
                        onClick={(e) => {
                            e.preventDefault();
                            navigate(NAVIGATE_PATHS.LOGIN);
                        }}
                    >
                        {getLabel("backToLogin")}
                    </a>
                </p>
            </div>
        </div>
    );
};

export default ForgotPassword;
