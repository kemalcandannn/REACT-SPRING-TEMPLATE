import React, { useEffect } from "react";
import { BiLogOut, BiTestTube } from "react-icons/bi";
import { useLanguage } from "../contexts/language/LanguageContext";
import { useAuthentication } from "../contexts/authentication/AuthenticationContext";

const Dashboard: React.FC = ({ }) => {
    const { getLabel } = useLanguage();
    const { logout } = useAuthentication();

    useEffect(() => {
        test();
    }, []);

    const test = () => {
        console.log("TEST BUTONUNA BASILDI");
    };

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

                    <div>
                        <button
                            onClick={test}>
                            <BiTestTube className="logo" /> {"TEST"}
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default Dashboard;