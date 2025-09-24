import { generatePath } from "react-router";
import { routeTemplates } from "./route-templates";

export const routes = {
    MAIN_PAGE: routeTemplates.MAIN_PAGE,
    SIGN_UP: routeTemplates.SIGN_UP,
    LOG_IN: routeTemplates.LOG_IN,
    ENTREPRENEUR_LANDING_PAGE: routeTemplates.ENTREPRENEUR_LANDING_PAGE,
    CREATE_ENTERPRISE: routeTemplates.CREATE_ENTERPRISE,
    ENTERPRISE: (enterpriseId: number) => generatePath(routeTemplates.ENTERPRISE, { enterpriseId }),
    CREATE_ENTERPRISE_EMPLOYEE: (enterpriseId: number) => generatePath(routeTemplates.CREATE_ENTERPRISE_EMPLOYEE, { enterpriseId }),
} satisfies Record<keyof typeof routeTemplates, string | ((...args: any[]) => string)>;