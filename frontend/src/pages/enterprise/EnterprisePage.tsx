import { Center, Text } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { StandardButton } from "../../common/StandardButton";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardHorizontalSeparator } from "../../common/StandardHorizontalSeparator";
import { StandardPanel } from "../../common/StandardPanel";
import { useIntParam } from "../../hooks/useIntParam";
import { routes } from "../../router/routes";
import { useContextOrThrow } from "../../utils/useContextOrThrow";
import { EnterpriseContext } from "./route/context/EnterpriseContext";
import { EmployeesScrollableList } from "./staff/EmployeesScrollableList";

export const EnterprisePage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const { enterpriseName, enterpriseDescription, enterpriseLocation } = useContextOrThrow(EnterpriseContext);

    const onCreateEmployeeAccountClick = () => {
        navigate(routes.createEnterpriseEmployee(enterpriseId))
    }

    return <Center height="100vh">
        <StandardPanel>
            <StandardFlex>
                <Text fontSize="xxl">{enterpriseName}</Text>
                <StandardHorizontalSeparator />
                <Text>{enterpriseDescription}</Text>
                <StandardHorizontalSeparator />
                <Text>{enterpriseLocation}</Text>
                <EmployeesScrollableList enterpriseId={enterpriseId} />
                <StandardButton
                    onClick={onCreateEmployeeAccountClick}>
                    Create an account for an employee of this enterprise
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center >
}