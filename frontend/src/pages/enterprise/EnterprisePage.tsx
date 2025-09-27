import { Text } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { StandardButton } from "../../common/StandardButton";
import { StanadardPanel } from "../../common/StandardPanel";
import { useIntParam } from "../../hooks/useIntParam";
import { routes } from "../../router/routes";
import { useContextOrThrow } from "../../utils/useContextOrThrow";
import { EnterpriseContext } from "./route/context/EnterpriseContext";

export const EnterprisePage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const { enterpriseName, enterpriseDescription, enterpriseLocation } = useContextOrThrow(EnterpriseContext);

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