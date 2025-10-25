export interface EventWithIdAndCapacity {
    start: Date;
    end: Date;
    resource: {
        tempId: string;
        capacity: number;
    };
}