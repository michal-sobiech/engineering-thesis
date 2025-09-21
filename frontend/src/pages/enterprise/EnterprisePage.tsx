import { Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { enterprisesApi } from "../../api/enterprises-api";
import { StanadardPanel } from "../../common/StandardPanel";
import { routes } from "../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../utils/result";
import { toInt } from "../../utils/string";

export const EnterprisePage = () => {
    console.log("EnterprisePAge.tsx");

    const { enterpriseId } = useParams<{ enterpriseId: string }>();
    const navigate = useNavigate();

    const [enterpriseName, setEnterpriseName] = useState<string>("");
    const [enterpriseDescription, setEnterpriseDescription] = useState<string>("");
    const [enterpriseLocation, setEnterpriseLocation] = useState<string>("");

    useEffect(() => {
        async function loadEnterpriseData(): Promise<void> {
            if (enterpriseId === undefined) {
                navigate(routes.MAIN_PAGE);
                return;
            }

            const enterpriseIdAsInt = toInt(enterpriseId);
            if (enterpriseIdAsInt === null) {
                navigate(routes.MAIN_PAGE)
                return;
            }

            const promise = enterprisesApi.getEnterprise(enterpriseIdAsInt);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.MAIN_PAGE)
                return;
            }

            setEnterpriseName(result.value.enterpriseName);
            setEnterpriseDescription(result.value.description);
            setEnterpriseLocation(result.value.location);
        }

        loadEnterpriseData();
    }, []);

    return <StanadardPanel>
        <Text>{enterpriseName}</Text>
        <Text>{enterpriseDescription}</Text>
        <Text>{enterpriseLocation}</Text>
    </StanadardPanel>
}