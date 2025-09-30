import { Box, Center } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import Message from "../../../../common/sign-up-page/sign_up_success/SignUpSuccessMessage";
import { StandardButton } from "../../../../common/StandardButton";
import { StanadardPanel } from "../../../../common/StandardPanel";
import { useIntParam } from "../../../../hooks/useIntParam";
import { routes } from "../../../../router/routes";

export const EmployeeCreationSuccessPage = () => {
    const navigate = useNavigate();
    const enterpriseId = useIntParam("enterpriseId");

    const onClick = () => {
        navigate(routes.enterprise(enterpriseId));
    }

    return <Center width="100vw" height="100vh">
        <Box>
            <StanadardPanel>
                <Center>
                    <Message />
                </Center>
                <StandardButton
                    onClick={onClick}>
                    Return to enterprise
                </StandardButton>
            </StanadardPanel>
        </Box>
    </Center>;
}