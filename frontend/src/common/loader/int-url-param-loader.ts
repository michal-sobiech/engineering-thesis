import { redirect } from "react-router";
import { routes } from "../../router/routes";
import { toInt } from "../../utils/string";

export function createIntUrlParamLoader(paramName: string) {
    return ({ params }: { params: any }) => {
        const paramValue = params[paramName];
        if (paramValue === undefined) {
            throw redirect(routes.mainPage);
        }

        const paramValueAsInt = toInt(paramValue);
        if (paramValueAsInt === null) {
            throw redirect(routes.mainPage);
        }

        return paramValueAsInt;
    }
}