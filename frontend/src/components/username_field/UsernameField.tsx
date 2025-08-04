import UsernameFieldProps from "./UsernameFieldProps";

const UsernameField = ({ className, username, setUsername }: UsernameFieldProps) => {

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(event.target.value);
    };

    return <input className={className}
        type="text"
        name="username"
        placeholder="Username"
        value={username}
        onChange={handleChange}
        required
    />;
}

export default UsernameField;