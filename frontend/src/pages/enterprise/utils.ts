import { err, ok, Result, ResultAsync } from "neverthrow";
import { enterprisesApi } from "../../api/enterprises-api";
import { fetchPhoto } from "../../utils/photo";
import { errorErrResultAsyncFromPromise, promiseResultToErrorAsyncResult } from "../../utils/result";

export interface EnterpriseData {
    name: string;
    description: string;
    location: string;
    logo: File | null;
    backgroundPhoto: File | null;
}

export function fetchEnterpriseData(enterpriseId: number): ResultAsync<EnterpriseData, Error> {
    async function createPromise(): Promise<Result<EnterpriseData, Error>> {
        const enterprisePromise = enterprisesApi.getEnterprise(enterpriseId);
        const enterpriseResult = await errorErrResultAsyncFromPromise(enterprisePromise);
        if (enterpriseResult.isErr()) {
            return err(enterpriseResult.error);
        }

        const logoPhotoId = enterpriseResult.value.logoPhotoId;
        const backgroundPhotoId = enterpriseResult.value.backgroundPhotoId;

        let logoFile: File | null;
        if (logoPhotoId === undefined) {
            logoFile = null;
        } else {
            const result = await fetchPhoto(logoPhotoId);
            if (result.isErr()) {
                return err(result.error);
            }
            logoFile = result.value;
        }

        let backgroundPhotoFile: File | null;
        if (backgroundPhotoId === undefined) {
            backgroundPhotoFile = null;
        } else {
            const result = await fetchPhoto(backgroundPhotoId);
            if (result.isErr()) {
                return err(result.error);
            }
            backgroundPhotoFile = result.value;
        }

        return ok<EnterpriseData>({
            name: enterpriseResult.value.name,
            description: enterpriseResult.value.description,
            location: enterpriseResult.value.location,
            logo: logoFile,
            backgroundPhoto: backgroundPhotoFile,
        });
    }

    return promiseResultToErrorAsyncResult(createPromise());

}