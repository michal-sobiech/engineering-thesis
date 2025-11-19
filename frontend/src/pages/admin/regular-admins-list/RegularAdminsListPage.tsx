import { Center, Text } from "@chakra-ui/react"
import { FC, useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { useRegularAdminsApi } from "../../../api/regular-admins-api"
import { ScrollableList } from "../../../common/ScrollableList"
import { StandardButton } from "../../../common/StandardButton"
import { StandardConcaveBox } from "../../../common/StandardConcaveBox"
import { StandardFlex } from "../../../common/StandardFlex"
import { StandardPanel } from "../../../common/StandardPanel"
import { GetRegularAdminResponse } from "../../../GENERATED-api"
import { routes } from "../../../router/routes"
import { DEFAULT_ERROR_MESSAGE_FOR_USER } from "../../../utils/error"
import { errorErrResultAsyncFromPromise } from "../../../utils/result"
import { toastError } from "../../../utils/toast"
import { RegularAdminsListPageContext } from "./RegularAdminsListPageContext"

export const RegularAdminsListPage = () => {
    const regularAdminsApi = useRegularAdminsApi();
    const navigate = useNavigate();

    const [regularAdmins, setRegularAdmins] = useState<GetRegularAdminResponse[]>([]);

    useEffect(() => {
        async function loadRegularAdmins() {
            const promise = regularAdminsApi.getRegularAdmins();
            const resultAsync = errorErrResultAsyncFromPromise(promise);
            const result = await resultAsync;
            if (result.isErr()) {
                toastError(DEFAULT_ERROR_MESSAGE_FOR_USER);
                navigate(routes.mainPage);
            }
        }
        loadRegularAdmins();
    }, []);

    const contextValue: RegularAdminsListPageContext = { regularAdmins, setRegularAdmins };

    const items = regularAdmins.map(admin => <ListItem {...admin} />);

    return <RegularAdminsListPageContext.Provider value={contextValue}>
        <Center height="100%">
            <StandardFlex>
                <StandardConcaveBox flex="1">
                    <ScrollableList height="100%">
                        {items}
                    </ScrollableList>
                </StandardConcaveBox>
                <StandardButton>
                    Add an admin
                </StandardButton>
            </StandardFlex>
        </Center>
    </RegularAdminsListPageContext.Provider>
}

const ListItem: FC<GetRegularAdminResponse> = ({ userId, username, firstName, lastName }) => {
    return <StandardPanel height="100%">
        <StandardFlex height="100%">
            <Text>
                User ID: {userId}
            </Text>
            <Text>
                Username: {username}
            </Text>
            <Text>
                {firstName} {lastName}
            </Text>
            <StandardButton>
                Edit
            </StandardButton>
        </StandardFlex>
    </StandardPanel>
}