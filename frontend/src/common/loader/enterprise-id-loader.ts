import { useLoaderData } from "react-router";
import { createIntUrlParamLoader } from "./int-url-param-loader";

export const enterpriseIdLoader = createIntUrlParamLoader("enterpriseId");

export const useEnterpriseIdFromLoader = () => {
    return useLoaderData() as number;
}