import { BiLogOut } from "react-icons/bi";
import { useLanguage } from "../contexts/language/LanguageContext";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";
import { useEffect } from "react";
import BaseApiAxios from "../helpers/BaseApiAxios";

const Dashboard: React.FC = ({ }) => {
    const { getLabel } = useLanguage();
    const { logout } = useAuthentication();

    useEffect(() => {
        getTestData();
    }, []);

    const getTestData = async () => {
        const response = await BaseApiAxios.get("/api/v1/test/random-integer-list");
        console.log(response);
    }

    return (
        <>
            <div style={{ margin: 0, display: "flex", placeItems: "center", minWidth: "320px", minHeight: "100vh" }}>
                <div style={{ margin: "0 auto", padding: "2rem", textAlign: "center" }}>
                    {getLabel("welcomeToHomePage")}

                    <div>
                        <button
                            onClick={logout}>
                            <BiLogOut className="logo" /> {getLabel("logout")}
                        </button>

                    </div>
                </div>
            </div>
        </>
    );
};

export default Dashboard;