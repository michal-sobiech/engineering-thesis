import { FC } from "react";
import { useReportsApi } from "../../api/reports-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError, toastSuccess } from "../../utils/toast";
import { ReportButton } from "./ReportButton";

export interface ReportEnterpriseButtonProps {
    enterpriseId: number,
}

export const ReportEnterpriseButton: FC<ReportEnterpriseButtonProps> = ({ enterpriseId }) => {
    const reportsApi = useReportsApi();

    const onClick = async () => {
        const promise = reportsApi.createReport({
            reportSubjectType: "ENTERPRISE",
            reportSubjectId: enterpriseId,
        });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        toastSuccess("Reported enterprise!");
    }

    return <ReportButton onClick={onClick} />
}