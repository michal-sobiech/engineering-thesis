import { EnterprisesApi, ResponseError } from "../../GENERATED-api";
import { extractContentDispositionHeaderValue } from "../../utils/http";
import { EnterpriseData } from "./EnterpriseData";

export async function fetchEnterpriseData(enterpriseId: number, enterprisesApi: EnterprisesApi): Promise<EnterpriseData> {
    const basicData = await enterprisesApi.getEnterprise(enterpriseId);

    const logo = await fetchEnterpriseLogo(enterpriseId, enterprisesApi);
    const backgroundPhoto = await fetchEnterpriseBackgroundPhoto(enterpriseId, enterprisesApi);

    return {
        name: basicData.name,
        description: basicData.description,
        location: basicData.location ?? null,
        logo,
        backgroundPhoto
    };

}

export async function fetchEnterpriseLogo(enterpriseId: number, enterprisesApi: EnterprisesApi): Promise<File | null> {
    try {
        const res = await enterprisesApi.getEnterpriseLogoPhotoRaw({ enterpriseId });

        const contentDispositionHeader = res.raw.headers.get("Content-Disposition");
        if (contentDispositionHeader === null) {
            return null;
        }
        const filename = extractContentDispositionHeaderValue(contentDispositionHeader);
        if (filename === null) {
            return null;
        }

        const body = await res.value();
        return new File([body], filename, { type: body.type });
    } catch (error: unknown) {
        if (error instanceof ResponseError && error.response.status === 204) {
            return null;
        }
        throw error;
    }
}

export async function fetchEnterpriseBackgroundPhoto(enterpriseId: number, enterprisesApi: EnterprisesApi): Promise<File | null> {
    try {
        const res = await enterprisesApi.getEnterpriseBackgroundPhotoRaw({ enterpriseId });

        const contentDispositionHeader = res.raw.headers.get("Content-Disposition");
        if (contentDispositionHeader === null) {
            return null;
        }
        const filename = extractContentDispositionHeaderValue(contentDispositionHeader);
        if (filename === null) {
            return null;
        }

        const body = await res.value();
        return new File([body], filename, { type: body.type });
    } catch (error: unknown) {
        if (error instanceof ResponseError && error.response.status === 204) {
            return null;
        }
        throw error;
    }
}
