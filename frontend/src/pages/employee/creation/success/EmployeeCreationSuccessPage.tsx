import { Box, Center } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import Message from "../../../../common/sign-up-page/sign_up_success/SignUpSuccessMessage";
import { StandardButton } from "../../../../common/StandardButton";
import { StandardFlex } from "../../../../common/StandardFlex";
import { StandardPanel } from "../../../../common/StandardPanel";
import { useIntParam } from "../../../../hooks/useIntParam";
import { routes } from "../../../../router/routes";

export const EmployeeCreationSuccessPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const onClick = () => {
        navigate(routes.enterprisePublic(enterpriseId));
    }

    return (<Center width="100%" height="100%">
        <Box>
            <StandardPanel>
                <StandardFlex>
                    <Center>
                        <Message />
                    </Center>
                    <StandardButton
                        onClick={onClick}>
                        Return to enterprise
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </Box>
    </Center>);
}