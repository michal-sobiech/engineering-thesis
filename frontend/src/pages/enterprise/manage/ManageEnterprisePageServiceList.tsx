import { FC, useEffect, useState } from "react";
import { useEnterprisesApi } from "../../../api/enterprises-api";
import { LinkScrollableList } from "../../../common/LinkScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { GetEnterpriseService200Response } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export interface ManageEnterprisePageServiceListProps {
    enterpriseId: number;
}

export const ManageEnterprisePageServiceList: FC<ManageEnterprisePageServiceListProps> = ({ enterpriseId }) => {
    const enterprisesApi = useEnterprisesApi();
    const [services, setServices] = useState<GetEnterpriseService200Response[]>([]);

    useEffect(() => {
        async function loadServicesData(): Promise<void> {
            const promise = enterprisesApi.getEnterpriseServices(enterpriseId);
            const result = await errorErrResultAsyncFromPromise(promise);
            if (result.isOk()) {
                setServices(result.value);
            }
        }

        loadServicesData();
    }, []);

    return <StandardBox>
        <LinkScrollableList items={services.map(service => ({
            label: service.name,
            url: routes.manageServicePage(service.serviceId),
        }))} />
    </StandardBox>;

}