import { EnterpriseEmployeesApi } from "../GENERATED-api";

export interface Employee {
    username: string;
    firstName: string;
    lastName: string;
}

export async function fetchEnterpriseEmployees(employeesApi: EnterpriseEmployeesApi, enterpriseId: number): Promise<Employee[]> {
    return (await employeesApi.getEnterpriseEmployees(enterpriseId)).map(item => ({
        username: item.username,
        firstName: item.firstName,
        lastName: item.lastName
    }));
}