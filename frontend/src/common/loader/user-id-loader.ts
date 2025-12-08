import { useLoaderData } from "react-router";
import { createIntUrlParamLoader } from "./int-url-param-loader";

export const userIdLoader = createIntUrlParamLoader("userId");

export const useUserIdFromLoader = () => {
    return useLoaderData() as number;
}