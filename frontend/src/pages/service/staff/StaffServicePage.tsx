import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useServicesApi } from "../../../api/services-api";
import { useServiceIdFromLoader } from "../../../common/loader/service-id-loader";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { toastError } from "../../../utils/toast";
import { StaffCustomServicePage } from "./custom/StaffCustomServicePage";
import { StaffNonCustomServicePage } from "./non-custom/StaffNonCustomServicePage";

export const StaffServicePage = () => {
    const serviceId = useServiceIdFromLoader();
    const servicesApi = useServicesApi();
    const navigate = useNavigate();

    const [isCustom, setIsCustom] = useState<boolean | null>(null);

    useEffect(() => {
        servicesApi.getServiceCustomAppointmentsStatus(serviceId)
            .then(response => {
                const isCustom = response.areCustomAppointmentsEnabled;
                setIsCustom(isCustom);
            })
            .catch(() => {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
            })
    }, []);

    if (isCustom === null) {
        return null;
    }

    return isCustom
        ? <StaffCustomServicePage />
        : <StaffNonCustomServicePage />;
}