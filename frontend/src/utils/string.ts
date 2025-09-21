export function toInt(string: string): number | null {
    const number = Number(string);
    return Number.isInteger(number) ? number : null;
}