import { useEffect, useState } from "react";
import { useSearchParams, useNavigate } from "react-router-dom";
import BaseApiAxios from "../../helpers/BaseApiAxios";
import { useLanguage } from "../../contexts/language/LanguageContext";
import { NAVIGATE_PATHS } from "../../constants/Paths";
import { useApiErrorHandler } from "../../helpers/ApiErrorHandler";
import "./style/Authorization.css";

const VerifyAccount: React.FC = () => {
    const { getLabel } = useLanguage();
    const navigate = useNavigate();
    const { handleApiError } = useApiErrorHandler();
    const [searchParams] = useSearchParams();
    const token = searchParams.get("token");

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    useEffect(() => {
        const verify = async () => {
            if (!token) {
                setError(getLabel("tokenNotFound"));
                setLoading(false);
                return;
            }

            try {
                await BaseApiAxios.post("api/v1/authentication/verify-account", { token });
                setSuccess(getLabel("accountVerifiedSuccessfully"));
                setTimeout(() => navigate(NAVIGATE_PATHS.LOGIN), 1600);
            } catch (err: any) {
                setError(handleApiError(err));
            } finally {
                setLoading(false);
            }
        };

        verify();
    }, [token]);

    return (
        <div className="login-body">
            <div className="login-root">
                <h2>{getLabel("verifyAccountTitle")}</h2>

                {loading && <div>{getLabel("loading")}...</div>}

                {!loading && error && (
                    <div className="error-text">{error}</div>
                )}

                {!loading && success && (
                    <div className="success-text">{success}</div>
                )}

                {!loading && (
                    <p>
                        <a
                            href="#"
                            onClick={(e: React.MouseEvent<HTMLAnchorElement>) => {
                                e.preventDefault();
                                navigate(NAVIGATE_PATHS.LOGIN);
                            }}
                        >
                            {getLabel("backToLogin")}
                        </a>
                    </p>
                )}
            </div>
        </div>
    );
};

export default VerifyAccount;
