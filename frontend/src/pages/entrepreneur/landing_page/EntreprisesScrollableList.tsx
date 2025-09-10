import { Text } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import { ReactElement, useEffect, useState } from "react";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { ScrollableList } from "../../../common/scrollable-list/ScrollableList";
import { GetEntrepreneurEnterprisesResponseItem } from "../../../GENERATED-api";
import { useAuthOrRedirect } from "../../../hooks/useAuthContext";
import { createDefaultResultAsyncfromPromise } from "../../../utils/result";

export const EnterprisesScrollableList = () => {
    const [itemsData, setItemsData] = useState<GetEntrepreneurEnterprisesResponseItem[] | null>(null);

    useEffect(() => {
        fetchEnterprises().then(result => result.isOk() && setItemsData(result.value));
    }, []);

    return <ScrollableList>
        {itemsData && createItems(itemsData)}
    </ScrollableList>;
}

function fetchEnterprises(): ResultAsync<GetEntrepreneurEnterprisesResponseItem[], string> {
    const auth = useAuthOrRedirect();
    const entrepreneurId = auth.entrepreneurId;
    const promise = entrepreneursApi.getEntrepreneurEnterprises(entrepreneurId);
    return createDefaultResultAsyncfromPromise(promise);
}

function createItems(data: GetEntrepreneurEnterprisesResponseItem[]): ReactElement[] {
    return data.map(createItem);
}

function createItem(data: GetEntrepreneurEnterprisesResponseItem): ReactElement {
    return <Text>
        {data.name}
    </Text>
}