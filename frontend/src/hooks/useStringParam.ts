import { redirect, useParams } from "react-router";
import { routes } from "../router/routes";

export function useStringParam(paramName: string): string {
    const params = useParams();

    const paramValue = params[paramName];
    if (paramValue === undefined) {
        throw redirect(routes.mainPage);
    }

    return paramValue;
}