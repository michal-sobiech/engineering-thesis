import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { servicesApi } from "../../../api/services-api";
import { useIntParam } from "../../../hooks/useIntParam";
import { routes } from "../../../router/routes";
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { toastError } from "../../../utils/toast";
import { NoCustomAppointmentsServicePublicPage } from "./NoCustomAppointmentsServicePublicPage";

export const ServicePublicPage = () => {
    const navigate = useNavigate();
    const serviceId = useIntParam("serviceId");

    const [areCustomAppointmentsEnabled, setAreCustomAppointmentsEnabled] = useState<boolean | null>(null);

    useEffect(() => {
        async function loadData() {
            const promise = servicesApi.getServiceCustomAppointmentsStatus(serviceId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
                return;
            }
            setAreCustomAppointmentsEnabled(result.value.areCustomAppointmentsEnabled);
        }
        loadData();
    }, []);

    if (areCustomAppointmentsEnabled) {
        // return <Service
        return <NoCustomAppointmentsServicePublicPage />;
    }
    else {
        return <NoCustomAppointmentsServicePublicPage />;
    }
}