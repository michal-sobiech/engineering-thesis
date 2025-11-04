export function getLastFromArray<T extends unknown>(array: T[]): T | undefined {
    return array.length === 0 ? undefined : array[-1];
}

export function sameElements<T>(a: T[], b: T[]): boolean {
    return a.length === b.length && a.every((value, index) => value === b[index]);
}