import { err, ok, okAsync, Result, ResultAsync } from "neverthrow";
import { photosApi } from "../api/photos-api";
import { isSuccessful } from "./http";
import { errorErrResultAsyncFromPromise, promiseResultToErrorAsyncResult } from "./result";

export function fetchPhoto(photoId: number): ResultAsync<File, Error> {
    async function createPromise(): Promise<Result<File, Error>> {
        const promise = photosApi.getPhotoRaw({ photoId });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            return err(result.error);
        }

        const status = result.value.raw.status;
        if (!isSuccessful(status)) {
            return err(new Error("Failed to fetch photo"));
        }

        const contentType = result.map(response => response.raw.headers.get("Content-Type"))
            .andThen(contentType => contentType === null
                ? err(new Error("Content-Type is null"))
                : ok(contentType)
            )
        if (contentType.isErr()) {
            return err(contentType.error);
        }

        const fileName = result.map(response => response.raw.headers.get("Content-Disposition"))
            .andThen(contentDisposition => contentDisposition === null
                ? err(new Error("Failed to find a Content-Disposition header"))
                : ok(contentDisposition)
            )
            .map(contentDisposition => contentDisposition.match(/filename="?([^",]+\.[^",]+)"?/i))
            .andThen(matchingResult => matchingResult === null
                ? err(new Error("Failed to find a file name in Content-Disposition header"))
                : ok(matchingResult)
            )
            .map(matchingResult => matchingResult[1]);
        if (fileName.isErr()) {
            return err(fileName.error);
        }

        const blob = await result.value.raw.blob();

        return ok(new File([blob], fileName.value, { type: contentType.value }));
    }

    return promiseResultToErrorAsyncResult(createPromise());
}

export function retrievePhoto(photoId: number | null): ResultAsync<File | null, Error> {
    if (photoId === null) {
        return okAsync(null);
    } else {
        return fetchPhoto(photoId).map(file => file ?? null);
    }
}