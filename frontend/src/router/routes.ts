import { generatePath } from "react-router";
import { routeTemplates } from "./route-templates";

export const routes = {
    mainPage: routeTemplates.mainPage,
    signUp: routeTemplates.signUp,
    logIn: routeTemplates.logIn,
    entrepreneurLandingPage: routeTemplates.entrepreneurLandingPage,
    createEnterprise: routeTemplates.createEnterprise,
    enterprise: (enterpriseId: number) => generatePath(routeTemplates.enterprise, { enterpriseId }),
    createEnterpriseEmployee: (enterpriseId: number) => generatePath(routeTemplates.createEnterpriseEmployee, { enterpriseId }),
} satisfies Record<keyof typeof routeTemplates, string | ((...args: any[]) => string)>;