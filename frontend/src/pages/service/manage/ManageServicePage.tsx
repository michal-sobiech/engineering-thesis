import { useEffect, useState } from "react";
import { useServicesApi } from "../../../api/services-api";
import { useServiceIdFromLoader } from "../../../common/loader/service-id-loader";
import { useNavigateWithToastDismiss } from "../../../hooks/useNavigateWithToastDismiss";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { ManageCustomServicePage } from "./custom/ManageCustomServicePage";
import { ManageNonCustomServicePage } from "./non-custom/ManageNonCustomServicePage";

export const ManageServicePage = () => {
    const navigate = useNavigateWithToastDismiss();
    const servicesApi = useServicesApi();
    const serviceId = useServiceIdFromLoader();

    const [isServiceCustom, setIsServiceCustom] = useState<boolean | null>(null)

    useEffect(() => {
        async function loadIsServiceCustom() {
            const promise = servicesApi.getServiceCustomAppointmentsStatus(serviceId);
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            setIsServiceCustom(result.value.areCustomAppointmentsEnabled);
        }
        loadIsServiceCustom();
    }, []);

    if (isServiceCustom === null) {
        return null;
    } else if (isServiceCustom) {
        return <ManageCustomServicePage />;
    } else {
        return <ManageNonCustomServicePage />;
    }

}