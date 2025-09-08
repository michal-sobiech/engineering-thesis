export function matchesEmailPattern(string: string): boolean {
    const pattern = /\S+@\S+\.\S+/;
    return pattern.test(string);
}