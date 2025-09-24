import { err, ok, Result } from "neverthrow";

export function validatePassword(password: string): Result<void, Error> {
    if (password === "") {
        return err(new Error("Password cannot be empty!"))
    } else if (password.length < 8) {
        return err(new Error("Password should be at least 8 characters long"));
    } else if (password.length > 64) {
        return err(new Error("Password shouldn't be longer than 64 characters"));
    }
    return ok();
}