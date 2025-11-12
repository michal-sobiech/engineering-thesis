import { FC, PropsWithChildren, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterprisesApi } from "../../../../api/enterprises-api";
import { Location } from "../../../../GENERATED-api";
import { useIntParam } from "../../../../hooks/useIntParam";
import { routes } from "../../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../../utils/result";
import { EnterpriseContext } from "./EnterpriseContext";
import { EnterpriseContextValue } from "./EnterpriseContextValue";

export const EnterpriseContextProvider: FC<PropsWithChildren> = ({ children }) => {
    const enterprisesApi = useEnterprisesApi();
    const navigate = useNavigate();

    const enterpriseId = useIntParam("enterpriseId");

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseDescription, setEnterpriseDescription] = useState<string>("");
    const [enterpriseLocation, setEnterpriseLocation] = useState<Location | null>(null);

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const promise = enterprisesApi.getEnterprise(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.mainPage)
                return;
            }

            setEnterpriseName(result.value.name);
            setEnterpriseDescription(result.value.description);
            setEnterpriseLocation(result.value.location ?? null);
        }

        loadEnterpriseData();
    }, []);


    const contextValue: EnterpriseContextValue = { enterpriseName, setEnterpriseName, enterpriseDescription, setEnterpriseDescription, enterpriseLocation, setEnterpriseLocation };

    return <EnterpriseContext.Provider value={contextValue}>
        {children}
    </EnterpriseContext.Provider>;
}