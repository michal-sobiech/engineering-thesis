export class UnauthenticatedError extends Error {
    constructor(message = "Unauthenticated") {
        super(message);
        this.name = "UnauthenticatedError ";
        Object.setPrototypeOf(this, new.target.prototype);
    }
}