import { createBrowserRouter } from "react-router-dom";
import { EmployeeCreationWizard } from "../pages/employee/creation/wizard/EmployeeCreationWizard";
import { EmployeeLogInPage } from "../pages/employee/log_in/EmployeeLogInPage";
import { EnterprisePage } from "../pages/enterprise/EnterprisePage";
import { EnterpriseRoute } from "../pages/enterprise/route/EnterpriseRoute";
import { CreateEnterprisePage } from "../pages/entrepreneur/create_enterprise/CreateEnterprisePage";
import { EntrepreneurLandingPage } from "../pages/entrepreneur/landing_page/EntrepreneurLandingPage";
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
                path: routeTemplates.entrepreneurSignUp,
                element: <EntrepreneurSignUpWizard />
            },
            {
                path: routeTemplates.entrepreneurSingUpOrLogIn,
                element: <EntrepreneurSignUpOrLogInPage />
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
                path: routeTemplates.enterprise,
                element: <EnterpriseRoute />,
                children: [
                    {
                        path: routeTemplates.enterprise,
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
                ]
            }
        ]
    }
])