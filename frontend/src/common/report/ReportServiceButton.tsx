import { FC } from "react";
import { useReportsApi } from "../../api/reports-api";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toastError, toastSuccess } from "../../utils/toast";
import { ReportButton } from "./template/ReportButton";

export interface ReportServiceButtonProps {
    serviceId: number,
}

export const ReportServiceButton: FC<ReportServiceButtonProps> = ({ serviceId }) => {
    const reportsApi = useReportsApi();

    const onClick = async () => {
        const promise = reportsApi.createReport({
            reportSubjectType: "ENTERPRISE_SERVICE",
            reportSubjectId: serviceId,
        });
        const result = await errorErrResultAsyncFromPromise(promise);
        if (result.isErr()) {
            toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
            return;
        }
        toastSuccess("Reported service!");
    }

    return <ReportButton onClick={onClick} />
}