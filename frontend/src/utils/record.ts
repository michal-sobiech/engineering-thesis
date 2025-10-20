export function typedEntries<K extends PropertyKey, V>(record: Record<K, V>): [K, V][] {
    return Object.entries(record) as [K, V][];
}