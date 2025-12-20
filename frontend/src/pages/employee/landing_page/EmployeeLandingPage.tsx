import { Center, Flex, Separator, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterpriseEmployeesApi } from "../../../api/enterprise-employees-api";
import { useEnterprisesApi } from "../../../api/enterprises-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardLabeledContainer } from "../../../common/StandardLabeledContainer";
import { StandardPanel } from "../../../common/StandardPanel";
import { routes } from "../../../router/routes";

export const EmployeeLandingPage = () => {
    const employeesApi = useEnterpriseEmployeesApi();
    const enterprisesApi = useEnterprisesApi();
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState<string>("");
    const [enterpriseName, setEnterprieName] = useState<string>("");
    const [enterpriseId, setEnterpriseId] = useState<number | null>(null);

    console.log("eeeeeeeeeee")

    useEffect(() => {
        employeesApi.getMeEmployee()
            .then(response => {
                setEnterpriseId(response.enterpriseId);
                setFirstName(response.firstName);
            })
            .catch(() => {
                navigate(routes.mainPage);
            });
    }, []);


    useEffect(() => {
        if (enterpriseId === null) {
            return;
        }

        enterprisesApi.getEnterprise(enterpriseId)
            .then(response => {
                setEnterprieName(response.name);
            })
            .catch(() => {
                navigate(routes.mainPage);
            });
    }, [enterpriseId]);

    const onCreateEnterpriseClick = () => {
        navigate(routes.createEnterprise);
    }

    const manageEnterpriseRoute = enterpriseId === null
        ? routes.mainPage
        : routes.manageEnterprise(enterpriseId);

    const staffPageRoute = enterpriseId === null
        ? routes.mainPage
        : routes.enterpriseStaff(enterpriseId);

    const publicPageRoute = enterpriseId === null
        ? routes.mainPage
        : routes.enterprisePublic(enterpriseId);

    return <Center height="100%">
        <StandardPanel width="80%" height="100%" padding="20px" >
            <StandardFlex>
                <Text fontSize="xl">
                    Hello, {firstName}!
                </Text>

                <Separator orientation="horizontal" width="100%" borderColor="primary.lightGray" />

                <StandardLabeledContainer label="Your enterprise:">
                    <StandardPanel>
                        <Flex direction="row" gap="10px" align="center">
                            <Text>
                                {enterpriseName}
                            </Text>
                            <StandardButton onClick={() => navigate(manageEnterpriseRoute)}>
                                Manage
                            </StandardButton>
                            <StandardButton onClick={() => navigate(staffPageRoute)}>
                                Edit
                            </StandardButton>
                            <StandardButton onClick={() => navigate(publicPageRoute)}>
                                Public view
                            </StandardButton>
                        </Flex>
                    </StandardPanel>
                </StandardLabeledContainer>
            </StandardFlex>
        </StandardPanel>
    </Center >;

}