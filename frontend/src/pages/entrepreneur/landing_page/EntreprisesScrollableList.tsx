import { Flex, Text } from "@chakra-ui/react";
import { ReactElement, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { StandardButton } from "../../../common/StandardButton";
import { StandardPanel } from "../../../common/StandardPanel";
import { GetEntrepreneurEnterprisesResponseItem } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export const EnterprisesScrollableList = () => {
    const navigate = useNavigate();
    const [itemsData, setItemsData] = useState<GetEntrepreneurEnterprisesResponseItem[] | null>(null);

    useEffect(() => {
        async function loadData(): Promise<void> {
            const me = await errorErrResultAsyncFromPromise(entrepreneursApi.getMeEntrepreneur());
            if (me.isErr()) {
                navigate(routes.mainPage);
                return;
            }

            const entrepreneurUserId = me.value.userId;
            const enterprises = await errorErrResultAsyncFromPromise(entrepreneursApi.getEntrepreneurEnterprises(entrepreneurUserId));
            if (enterprises.isErr()) {
                navigate(routes.mainPage);
                return;
            }

            setItemsData(enterprises.value);
        }

        loadData();
    }, []);

    return <StandardBox>
        <ScrollableList>
            {itemsData && createItems(itemsData)}
        </ScrollableList>
    </StandardBox>;
}

function createItems(data: GetEntrepreneurEnterprisesResponseItem[]): ReactElement[] {
    return data.map(itemData => <ListItem data={itemData} />);
}

const ListItem = ({ data }: { data: GetEntrepreneurEnterprisesResponseItem }) => {
    const navigate = useNavigate();

    const publicPageUrl = routes.enterprisePublic(data.enterpriseId);
    const staffPageUrl = routes.enterpriseStaff(data.enterpriseId);

    return <StandardPanel>
        <Flex direction="row" gap="5px">
            <Text>
                {data.enterpriseName}
            </Text>
            <StandardButton onClick={() => navigate(publicPageUrl)}>
                Public view
            </StandardButton>
            <StandardButton onClick={() => navigate(staffPageUrl)}>
                Edit
            </StandardButton>
        </Flex>
    </StandardPanel>;

}