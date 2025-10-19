import { err, ok, Result, ResultAsync } from "neverthrow";
import { enterprisesApi } from "../../api/enterprises-api";
import { retrievePhoto } from "../../utils/photo";
import { errorErrResultAsyncFromPromise, promiseResultToErrorAsyncResult } from "../../utils/result";
import { EnterpriseData } from "./EnterpriseData";

export function fetchEnterpriseData(enterpriseId: number): ResultAsync<EnterpriseData, Error> {
    async function createPromise(): Promise<Result<EnterpriseData, Error>> {
        const enterprisePromise = enterprisesApi.getEnterprise(enterpriseId);
        const enterpriseResult = await errorErrResultAsyncFromPromise(enterprisePromise);
        if (enterpriseResult.isErr()) {
            return err(enterpriseResult.error);
        }

        const logoPhotoId = enterpriseResult.value.logoPhotoId ?? null;
        const backgroundPhotoId = enterpriseResult.value.backgroundPhotoId ?? null;

        let logoFile = await retrievePhoto(logoPhotoId);
        if (logoFile.isErr()) {
            throw logoFile.error;
        }

        let backgroundPhotoFile = await retrievePhoto(backgroundPhotoId);
        if (backgroundPhotoFile.isErr()) {
            throw backgroundPhotoFile.error;
        }

        return ok<EnterpriseData>({
            name: enterpriseResult.value.name,
            description: enterpriseResult.value.description,
            location: enterpriseResult.value.location,
            logo: logoFile.value,
            backgroundPhoto: backgroundPhotoFile.value,
        });
    }

    return promiseResultToErrorAsyncResult(createPromise());

}
