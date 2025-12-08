import { useLoaderData } from "react-router";
import { createIntUrlParamLoader } from "./int-url-param-loader";

export const serviceIdLoader = createIntUrlParamLoader("serviceId");

export const useServiceIdFromLoader = () => {
    return useLoaderData() as number;
}