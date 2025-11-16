import { generatePath } from "react-router";
import { routeTemplates } from "./route-templates";

export const routes = {
    mainPage: routeTemplates.mainPage,
    customerSignUp: routeTemplates.customerSignUp,
    customerLogIn: routeTemplates.customerLogIn,
    customerSignUpOrLogIn: routeTemplates.customerSignUpOrLogIn,
    customerLandingPage: routeTemplates.customerLandingPage,
    enterpriseStaffSignUpOrLogIn: routeTemplates.enterpriseStaffSignUpOrLogIn,
    entrepreneurSignUp: routeTemplates.entrepreneurSignUp,
    entrepreneurLogIn: routeTemplates.entrepreneurLogIn,
    entrepreneurLandingPage: routeTemplates.entrepreneurLandingPage,
    createEnterprise: routeTemplates.createEnterprise,
    enterprisePublic: (enterpriseId: number) => generatePath(routeTemplates.enterprisePublic, { enterpriseId }),
    enterpriseStaff: (enterpriseId: number) => generatePath(routeTemplates.enterpriseStaff, { enterpriseId }),
    createEnterpriseEmployee: (enterpriseId: number) => generatePath(routeTemplates.createEnterpriseEmployee, { enterpriseId }),
    logInEnterpriseEmployee: routeTemplates.logInEnterpriseEmployee,
    enterpriseCreateService: (enterpriseId: number) => generatePath(routeTemplates.enterpriseCreateService, { enterpriseId }),
    serviceSearch: routeTemplates.serviceSearch,
    servicePublicPage: (serviceId) => generatePath(routeTemplates.servicePublicPage, { serviceId }),
    createServiceReview: (serviceId) => generatePath(routeTemplates.createServiceReview, { serviceId }),
    adminReportsPage: routeTemplates.adminReportsPage,
    adminLogIn: routeTemplates.adminLogIn,
} satisfies Record<keyof typeof routeTemplates, string | ((...args: any[]) => string)>;