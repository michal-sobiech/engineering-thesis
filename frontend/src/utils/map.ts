export function reverseMap<K, V>(map: Map<K, V>): Map<V, K> {
    return new Map<V, K>(Array.from(map.entries()).map(([k, v]) => [v, k]));
}