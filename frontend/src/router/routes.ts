import { generatePath } from "react-router";
import { routeTemplates } from "./route-templates";

export const routes = {
    mainPage: routeTemplates.mainPage,
    customerSignUp: routeTemplates.customerSignUp,
    customerLogIn: routeTemplates.customerLogIn,
    entrepreneurSignUp: routeTemplates.entrepreneurSignUp,
    entrepreneurLogIn: routeTemplates.entrepreneurLogIn,
    entrepreneurLandingPage: routeTemplates.entrepreneurLandingPage,
    createEnterprise: routeTemplates.createEnterprise,
    enterprise: (enterpriseId: number) => generatePath(routeTemplates.enterprise, { enterpriseId }),
    createEnterpriseEmployee: (enterpriseId: number) => generatePath(routeTemplates.createEnterpriseEmployee, { enterpriseId }),
    logInEnterpriseEmployee: routeTemplates.logInEnterpriseEmployee,
} satisfies Record<keyof typeof routeTemplates, string | ((...args: any[]) => string)>;