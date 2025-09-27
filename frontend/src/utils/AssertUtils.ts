function assertDefined<T>(value: T, exceptionMessage?: string): NonNullable<T> {
    return exceptionMessage === undefined
        ? assertDefinedNoMessage(value)
        : assertDefinedWithMessage(value, exceptionMessage);
}

function assertDefinedWithMessage<T>(value: T, exceptionMessage: string): NonNullable<T> {
    if (value === null || value === undefined) {
        throw new TypeError(exceptionMessage);
    } else {
        return value;
    }
}

function assertDefinedNoMessage<T>(value: T): NonNullable<T> {
    const exceptionMessage = "Value is nullable but shouldn't be";
    return assertDefined(value, exceptionMessage);
}

export { assertDefined };
