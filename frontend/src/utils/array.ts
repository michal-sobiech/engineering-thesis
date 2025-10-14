export function getLast<T extends unknown>(array: T[]): T | undefined {
    return array.length === 0 ? undefined : array[-1];
}