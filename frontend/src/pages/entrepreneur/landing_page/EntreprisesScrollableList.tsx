import { Text } from "@chakra-ui/react";
import { ResultAsync } from "neverthrow";
import { ReactElement, useEffect, useState } from "react";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { AuthContextValue } from "../../../auth-context/authContext";
import { ScrollableList } from "../../../common/scrollable-list/ScrollableList";
import { GetEntrepreneurEnterprisesResponseItem } from "../../../GENERATED-api";
import { useWithAuth } from "../../../hooks/useWithAuth";
import { defaultStringErrResultAsyncFromPromise, fromNullable, fromResult } from "../../../utils/result";

export const EnterprisesScrollableList = () => {
    const [itemsData, setItemsData] = useState<GetEntrepreneurEnterprisesResponseItem[] | null>(null);
    const withAuth = useWithAuth();

    useEffect(() => {
        async function fetchAndSetItemsData(authContextValue: AuthContextValue): Promise<void> {
            const entrepreneurId = fromNullable(authContextValue.auth).map(auth => auth.entrepreneurId);
            const data = await fromResult(entrepreneurId).andThen(fetchEnterprisesData);
            if (data.isOk()) {
                setItemsData(data.value);
            }
        }

        // withAuth(fetchAndSetItemsData);
    }, []);

    return <ScrollableList>
        {itemsData && createItems(itemsData)}
    </ScrollableList>;
}

function fetchEnterprisesData(entrepreneurId: number): ResultAsync<GetEntrepreneurEnterprisesResponseItem[], string> {
    const promise = entrepreneursApi.getEntrepreneurEnterprises(entrepreneurId);
    return defaultStringErrResultAsyncFromPromise(promise);
}

function createItems(data: GetEntrepreneurEnterprisesResponseItem[]): ReactElement[] {
    return data.map(createItem);
}

function createItem(data: GetEntrepreneurEnterprisesResponseItem): ReactElement {
    return <Text>
        {data.name}
    </Text>
}