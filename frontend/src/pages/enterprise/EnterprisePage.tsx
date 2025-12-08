import { Center, Text } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { useEnterpriseIdFromLoader } from "../../common/loader/enterprise-id-loader";
import { StandardButton } from "../../common/StandardButton";
import { StandardFlex } from "../../common/StandardFlex";
import { StandardHorizontalSeparator } from "../../common/StandardHorizontalSeparator";
import { StandardPanel } from "../../common/StandardPanel";
import { useContextOrThrow } from "../../hooks/useContextOrThrow";
import { routes } from "../../router/routes";
import { EnterpriseContext } from "./route/context/EnterpriseContext";
import { EmployeesScrollableList } from "./staff/EmployeesScrollableList";

export const EnterprisePage = () => {
    const navigate = useNavigate();
    const enterpriseId = useEnterpriseIdFromLoader();

    const { enterpriseName, enterpriseDescription, enterpriseLocation } = useContextOrThrow(EnterpriseContext);

    const onCreateEmployeeAccountClick = () => {
        navigate(routes.createEnterpriseEmployee(enterpriseId))
    }

    return <Center height="100%">
        <StandardPanel>
            <StandardFlex>
                <Text fontSize="xxl">{enterpriseName}</Text>
                <StandardHorizontalSeparator />
                <Text>{enterpriseDescription}</Text>
                <StandardHorizontalSeparator />
                <Text>{enterpriseLocation?.address}</Text>
                <EmployeesScrollableList enterpriseId={enterpriseId} />
                <StandardButton
                    onClick={onCreateEmployeeAccountClick}>
                    Create an account for an employee of this enterprise
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center >
}