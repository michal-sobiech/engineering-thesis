import { Center, Text } from "@chakra-ui/react";
import { useNavigate } from "react-router";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardPanel } from "../../../common/StandardPanel";
import { routes } from "../../../router/routes";

export const EnterpriseStaffSignUpOrLogIn = () => {
    const navigate = useNavigate();

    return <Center height="100%">
        <StandardFlex width="30%">
            <StandardPanel>
                <StandardFlex>
                    <Text fontSize="xxl">Entrepreneur (enterprise owner)</Text>
                    <StandardButton onClick={() => navigate(routes.entrepreneurLogIn)}>
                        Log In
                    </StandardButton>
                    <StandardButton onClick={() => navigate(routes.entrepreneurSignUp)}>
                        Sign up
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
            <StandardPanel>
                <StandardFlex>
                    <Text fontSize="xxl">Enterprise employee</Text>
                    <StandardButton onClick={() => navigate(routes.logInEnterpriseEmployee)}>
                        Log In
                    </StandardButton>
                </StandardFlex>
            </StandardPanel>
        </StandardFlex>
    </Center>;
}