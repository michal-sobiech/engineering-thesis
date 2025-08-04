import PasswordFieldProps from "./PasswordFieldProps";

const PasswordField = ({ className, password, setPassword }: PasswordFieldProps) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    }

    return <input className={className}
        type="password"
        name="password"
        value={password}
        onChange={handleChange}
        placeholder="Password"
        required
    />;
}

export default PasswordField;