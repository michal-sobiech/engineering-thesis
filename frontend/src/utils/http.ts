export function isSuccessful(httpStatus: number): boolean {
    return httpStatus >= 200 && httpStatus < 300;
}