import { redirect, useLoaderData } from "react-router";
import { routes } from "../../router/routes";

export function createStringUrlParamLoader(paramName: string) {
    return ({ params }: { params: any }) => {
        const paramValue = params[paramName];
        if (paramValue === undefined) {
            throw redirect(routes.mainPage);
        }
        if (typeof paramValue !== "string") {
            throw redirect(routes.mainPage);
        }

        return paramValue;
    }
}

export const useStringParamFromLoader = () => {
    return useLoaderData() as string;
}