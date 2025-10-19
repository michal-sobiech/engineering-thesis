import { Box, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { enterprisesApi } from "../../../api/enterprises-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { GetEnterpriseServicesResponseItem } from "../../../GENERATED-api";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export const EnterprisePublicPageServicesList = ({ enterpriseId }: { enterpriseId: number }) => {
    const [services, setServices] = useState<GetEnterpriseServicesResponseItem[]>([]);

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
        <ScrollableList>
            {services.map(createServiceBox)}
        </ScrollableList>
    </StandardBox>
}

function createServiceBox(data: GetEnterpriseServicesResponseItem) {
    return <Box>
        <Text>{data.name}</Text>
    </Box>
}