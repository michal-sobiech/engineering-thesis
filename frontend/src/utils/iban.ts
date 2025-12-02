export function isIban(value: string): boolean {
    const pattern = /^[A-Z]{2}[0-9]{2}[A-Z0-9]{30}/
    return pattern.test(value);
}