import { Box, Text } from "@chakra-ui/react";
import { FC, ReactElement, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useEnterpriseEmployeesApi } from "../../../api/enterprise-employees-api";
import { ScrollableList } from "../../../common/ScrollableList";
import { StandardBox } from "../../../common/StandardBox";
import { GetEnterpriseEmployeesResponseItem } from "../../../GENERATED-api";
import { routes } from "../../../router/routes";
import { errorErrResultAsyncFromPromise } from "../../../utils/result";

export interface EmployeesScrollableListProps {
    enterpriseId: number;
}

export const EmployeesScrollableList: FC<EmployeesScrollableListProps> = ({ enterpriseId }) => {
    const enterpriseEmployeesApi = useEnterpriseEmployeesApi();
    const navigate = useNavigate();
    const [employees, setEmployees] = useState<GetEnterpriseEmployeesResponseItem[]>([]);

    useEffect(() => {
        async function loadData(): Promise<void> {
            let employees = await errorErrResultAsyncFromPromise(enterpriseEmployeesApi.getEnterpriseEmployees(enterpriseId));
            if (employees.isErr()) {
                navigate(routes.mainPage);
                return;
            }
            const employees2 = new Array(50).fill(employees.value[0])
            setEmployees(employees2);
        }
        loadData();
    }, []);

    return <Box>
        <Text>Employees</Text>
        <StandardBox>
            <ScrollableList>
                {createItems(employees)}
            </ScrollableList>
        </StandardBox>
    </Box>;
};

function createItems(employees: GetEnterpriseEmployeesResponseItem[]): ReactElement[] {
    return employees.map(createItem);
}

function createItem(employee: GetEnterpriseEmployeesResponseItem): ReactElement {
    return <Box>
        <Text>{employee.username}</Text>
        <Text>{employee.firstName}</Text>
        <Text>{employee.lastName}</Text>
    </Box>
}