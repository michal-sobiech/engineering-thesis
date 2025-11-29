export const postalCodePattern = /^\d{2}-\d{3}$/;

export function isPostalCode(value: string): boolean {
    return postalCodePattern.test(value);
}