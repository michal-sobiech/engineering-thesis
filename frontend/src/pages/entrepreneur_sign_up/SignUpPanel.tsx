import { useState } from "react";
import PasswordField from "../../components/password_field/PasswordField";
import UsernameField from "../../components/username_field/UsernameField";

import styles from "./EntrepreneurSignUpPage.module.scss";

const SignUpPanel = () => {
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    return <div className={styles["sign-up-panel"]}>
        <UsernameField className={styles["sign-up-panel__username-field"]} username={username} setUsername={setUsername} />
        <PasswordField className={styles["sign-up-panel__password-field"]} password={password} setPassword={setPassword} />
    </div>;
};

export default SignUpPanel;