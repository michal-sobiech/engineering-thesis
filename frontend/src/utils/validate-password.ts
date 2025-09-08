import { err, ok, Result } from "neverthrow";

export function validatePassword(password: string): Result<void, string> {
    if (password === "") {
        return err("Password cannot be empty!")
    } else if (password.length < 8) {
        return err("Password should be at least 8 characters long");
    } else if (password.length > 64) {
        return err("Password shouldn't be longer than 64 characters");
    }
    return ok();
}