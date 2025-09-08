export function validateEmail(email: string): boolean {
    const pattern = /\S+@\S+\.\S+/;
    return pattern.test(email);
}