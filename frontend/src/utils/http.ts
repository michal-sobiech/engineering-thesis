export function isSuccessful(httpStatus: number): boolean {
    return httpStatus >= 200 && httpStatus < 300;
}

export function extractContentDispositionHeaderValue(contentDispositionHeader: string): string | null {
    const pattern = /inline; filename="(.+)"/;
    const matchResult = contentDispositionHeader?.match(pattern);
    if (matchResult === null) {
        return null;
    }

    return matchResult[1];
}

export function extractFileExtensionsFromContentTypeHeader(contentTypeHeader: string): string | null {
    return contentTypeHeader.split("/").at(1) ?? null;
}