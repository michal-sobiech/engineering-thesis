import { createBrowserRouter } from "react-router-dom";
import { EmployeeCreationWizard } from "../pages/employee/creation/wizard/EmployeeCreationWizard";
import { EmployeeLogInPage } from "../pages/employee/log_in/EmployeeLogInPage";
import { EnterprisePageForStaff } from "../pages/enterprise/enterprise-staff/EnterprisePageForStaff";
import { EnterprisePage } from "../pages/enterprise/EnterprisePage";
import { CreateEnterprisePage } from "../pages/entrepreneur/create_enterprise/CreateEnterprisePage";
import { EntrepreneurLandingPage } from "../pages/entrepreneur/landing_page/EntrepreneurLandingPage";
import { EntrepreneurLogInPage } from "../pages/entrepreneur/log-in/EntrepreneurLogInPage";
import { EntrepreneurSignUpOrLogInPage } from "../pages/entrepreneur/sign-up-or-log-in/EntrepreneurSignUpOrLogInPage";
import { EntrepreneurSignUpWizard } from "../pages/entrepreneur/sign-up/EntrepreneurSignUpWizard";
import { MainPage } from "../pages/main_page/MainPage";
import { PageLayout } from "./PageLayout";
import { ProtectedRoute } from "./ProtectedRoute";
import { routeTemplates } from "./route-templates";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <PageLayout />,
        children: [
            {
                index: true,
                element: <MainPage />
            },
            {
                path: routeTemplates.entrepreneurSingUpOrLogIn,
                element: <EntrepreneurSignUpOrLogInPage />
            },
            {
                path: routeTemplates.entrepreneurSignUp,
                element: <EntrepreneurSignUpWizard />
            },
            {
                path: routeTemplates.entrepreneurLogIn,
                element: <EntrepreneurLogInPage />
            },
            {
                path: routeTemplates.entrepreneurLandingPage,
                element: <ProtectedRoute>
                    <EntrepreneurLandingPage />
                </ProtectedRoute>
            },
            {
                path: routeTemplates.createEnterprise,
                element: <ProtectedRoute>
                    <CreateEnterprisePage />
                </ProtectedRoute>
            },
            {
                path: routeTemplates.enterprisePublic,
                element: <EnterprisePage />
            },
            {
                path: routeTemplates.createEnterpriseEmployee,
                element: <EmployeeCreationWizard />
            },
            {
                path: routeTemplates.logInEnterpriseEmployee,
                element: <EmployeeLogInPage />
            },
            {
                path: routeTemplates.enterpriseStaff,
                element: <EnterprisePageForStaff />
            }
        ]
    }
])