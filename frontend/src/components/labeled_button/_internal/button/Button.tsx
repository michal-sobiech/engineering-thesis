import styles from "./EntrepreneurSignUpPage.module.scss";
import LabeledButtonProps from "./props";


const Button = ({ onClick }: LabeledButtonProps) => {
    return <div >
        <button className={styles["sign-up-panel__button"]} onClick={onClick}>
            Sign up
        </button>
    </div>
};

export default Button;