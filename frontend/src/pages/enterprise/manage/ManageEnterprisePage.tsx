import { Box, Center } from "@chakra-ui/react";
import { useEnterpriseIdFromLoader } from "../../../common/loader/enterprise-id-loader";
import { StandardPanel } from "../../../common/StandardPanel";
import { ManageEnterprisePageServiceList } from "./ManageEnterprisePageServiceList";

export const ManageEnterprisePage = () => {
    const entepriseId = useEnterpriseIdFromLoader();

    return <Center height="100%" overflowY="scroll">
        <Box width="80%" height="100%">
            <StandardPanel height="100%">
                <ManageEnterprisePageServiceList enterpriseId={entepriseId} />
            </StandardPanel>
        </Box>
    </Center>;

}