import { ResultAsync } from "neverthrow";
import { ReactElement } from "react";
import { entrepreneursApi } from "../../../api/entrepreneurs-api";
import { ScrollableList } from "../../../common/scrollable-list/ScrollableList";
import { GetEntrepreneurEnterprisesResponseItem } from "../../../GENERATED-api";
import { createDefaultResultAsyncfromPromise } from "../../../utils/result";

export const EnterprisesScrollableList = () => {
    const children: ReactElement[] = [];

    return <ScrollableList>
        {children}
    </ScrollableList>;
}

// function createChildren(): ResultAsync<ReactElement[], string> {
//     const enterpreneurId = authContext?.entrepreneurId;
//     const result = fetchEnterprises(enterpreneurId);
//     return result.map()
// }

function fetchEnterprises(entrepreneurId: number): ResultAsync<GetEntrepreneurEnterprisesResponseItem[], string> {
    const promise = entrepreneursApi.getEntrepreneurEnterprises(entrepreneurId);
    return createDefaultResultAsyncfromPromise(promise);
}

// function createItem(data: GetEntrepreneurEnterprisesResponseItem): ReactElement