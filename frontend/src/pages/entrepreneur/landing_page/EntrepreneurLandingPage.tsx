import { Center, Separator, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEntrepreneursApi } from "../../../api/entrepreneurs-api";
import { StandardButton } from "../../../common/StandardButton";
import { StandardFlex } from "../../../common/StandardFlex";
import { StandardPanel } from "../../../common/StandardPanel";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";
import { EnterprisesScrollableList } from "./EntreprisesScrollableList";

export const EntrepreneurLandingPage = () => {
    const entrepreneursApi = useEntrepreneursApi();
    const navigate = useNavigate();

    const [firstName, setFirstName] = useState<string>("");

    useEffect(() => {
        async function loadData() {
            const promise = entrepreneursApi.getMeEntrepreneur();
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isErr()) {
                navigate(routes.mainPage);
                return
            }
            setFirstName(result.value.firstName);
        }
        loadData();
    }, []);

    const onCreateEnterpriseClick = () => {
        navigate(routes.createEnterprise);
    }

    return <Center height="100%">
        <StandardPanel width="80%" height="100%" padding="20px" >
            <StandardFlex>
                <Text fontSize="xl">
                    Hello, {firstName}!
                </Text>
                <Separator orientation="horizontal" width="100%" borderColor="primary.lightGray" />
                <Text>
                    Your enterprises
                </Text>
                <EnterprisesScrollableList />
                <StandardButton onClick={onCreateEnterpriseClick}>
                    Create an enterprise
                </StandardButton>
            </StandardFlex>
        </StandardPanel>
    </Center >;

}