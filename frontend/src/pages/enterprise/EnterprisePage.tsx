import { Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { enterprisesApi } from "../../api/enterprises-api";
import { StandardButton } from "../../common/StandardButton";
import { StanadardPanel } from "../../common/StandardPanel";
import { useIntParam } from "../../hooks/useIntParam";
import { routes } from "../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../utils/result";

export const EnterprisePage = () => {
    console.log("EnterprisePage.tsx");

    const enterpriseId = useIntParam("enterpriseId");

    const navigate = useNavigate();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseDescription, setEnterpriseDescription] = useState<string>("");
    const [enterpriseLocation, setEnterpriseLocation] = useState<string>("");

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            const promise = enterprisesApi.getEnterprise(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.mainPage)
                return;
            }

            setEnterpriseName(result.value.enterpriseName);
            setEnterpriseDescription(result.value.description);
            setEnterpriseLocation(result.value.location);
        }

        loadEnterpriseData();
    }, []);

    const onCreateEmployeeAccountClick = () => {
        navigate(routes.createEnterpriseEmployee(enterpriseId))
    }

    return <StanadardPanel>
        <Text>{enterpriseName}</Text>
        <Text>{enterpriseDescription}</Text>
        <Text>{enterpriseLocation}</Text>
        <StandardButton
            onClick={onCreateEmployeeAccountClick}>
            Create an account for an employee of this enterprise
        </StandardButton>
    </StanadardPanel>
}