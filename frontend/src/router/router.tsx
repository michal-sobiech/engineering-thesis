import { createBrowserRouter } from "react-router-dom";
import { CustomerLandingPage } from "../pages/customer/CustomerLandingPage";
import { CustomerLogInPage } from "../pages/customer/CustomerLogInPage";
import { CustomerSignUpOrLogIn } from "../pages/customer/CustomerSignUpOrLogInPage";
import { CustomerSignUpPage } from "../pages/customer/CustomerSignUpPage";
import { EmployeeCreationWizard } from "../pages/employee/creation/wizard/EmployeeCreationWizard";
import { EmployeeLogInPage } from "../pages/employee/log_in/EmployeeLogInPage";
import { EnterprisePublicPage } from "../pages/enterprise/public/EnterprisePublicPage";
import { EnterpriseStaffPage } from "../pages/enterprise/staff/EnterpriseStaffPage";
import { CreateEnterprisePage } from "../pages/entrepreneur/create_enterprise/CreateEnterprisePage";
import { EntrepreneurLandingPage } from "../pages/entrepreneur/landing_page/EntrepreneurLandingPage";
import { EntrepreneurLogInPage } from "../pages/entrepreneur/log-in/EntrepreneurLogInPage";
import { EntrepreneurSignUpOrLogInPage } from "../pages/entrepreneur/sign-up-or-log-in/EntrepreneurSignUpOrLogInPage";
import { EntrepreneurSignUpWizard } from "../pages/entrepreneur/sign-up/EntrepreneurSignUpWizard";
import { MainPage } from "../pages/main_page/MainPage";
import { ServiceCreationPage } from "../pages/service/ServiceCreationPage";
import { ServicePublicPage } from "../pages/service/ServicePublicPage";
import { ServiceSearchPage } from "../pages/service/search/ServiceSearchPage";
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
                path: routeTemplates.entrepreneurSignUpOrLogIn,
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
                path: routeTemplates.createEnterpriseEmployee,
                element: <EmployeeCreationWizard />
            },
            {
                path: routeTemplates.logInEnterpriseEmployee,
                element: <EmployeeLogInPage />
            },
            {
                path: routeTemplates.enterprisePublic,
                element: <EnterprisePublicPage />
            },
            {
                path: routeTemplates.enterpriseStaff,
                element: <EnterpriseStaffPage />
            },
            {
                path: routeTemplates.enterpriseCreateService,
                element: <ServiceCreationPage />
            },
            {
                path: routeTemplates.serviceSearch,
                element: <ServiceSearchPage />
            },
            {
                path: routeTemplates.servicePublicPage,
                element: <ServicePublicPage />
            },
            {
                path: routeTemplates.customerSignUp,
                element: <CustomerSignUpPage />
            },
            {
                path: routeTemplates.customerSignUpOrLogIn,
                element: <CustomerSignUpOrLogIn />
            },
            {
                path: routeTemplates.customerLandingPage,
                element: <CustomerLandingPage />
            },
            {
                path: routeTemplates.customerLogIn,
                element: <CustomerLogInPage />
            }
        ]
    }
])