export interface EventWithIdAndCapacity {
    start: Date;
    end: Date;
    resource: {
        instanceId: string;
        capacity: number;
    };
}