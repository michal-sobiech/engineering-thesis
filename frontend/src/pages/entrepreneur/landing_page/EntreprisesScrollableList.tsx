import { ReactElement, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { LinkText } from "../../../common/LinkText";
import { ScrollableList } from "../../../common/scrollable-list/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
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

            const entrepreneurId = me.value.entrepreneurId;
            const enterprises = await errorErrResultAsyncFromPromise(entrepreneursApi.getEntrepreneurEnterprises(entrepreneurId));
            if (enterprises.isErr()) {
                navigate(routes.mainPage);
                return;
            }

            // setItemsData(enterprises.value);
            const fakeEnterprises = new Array(50).fill(enterprises.value[0])
            setItemsData(fakeEnterprises);
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
    return data.map(createItem);
}

function createItem(data: GetEntrepreneurEnterprisesResponseItem): ReactElement {
    // return <Text fontSize="xs">
    //     {data.name}
    // </Text>
    const url = routes.enterprisePublic(data.enterpriseId)

    return <LinkText url={url} fontSize="xs">
        {data.enterpriseName}
    </LinkText>
}