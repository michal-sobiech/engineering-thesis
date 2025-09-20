import { createBrowserRouter } from "react-router-dom";
import { CreateEnterprisePage } from "../pages/entrepreneur/create_enterprise/CreateEnterprisePage";
import { EntrepreneurLandingPage } from "../pages/entrepreneur/landing_page/EntrepreneurLandingPage";
import LogInPage from "../pages/log_in/LogInPage";
import { SignUpWizard } from "../pages/sign_up/wizard/SignUpWizard";
import { PageLayout } from "./PageLayout";
import { ProtectedRoute } from "./ProtectedRoute";
import { routes } from "./routes";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <PageLayout />,
        children: [
            {
                index: true,
                element: <LogInPage />
            },
            {
                path: routes.LOG_IN,
                element: <LogInPage />
            },
            {
                path: routes.SIGN_UP,
                element: <SignUpWizard />
            },
            {
                path: routes.ENTREPRENEUR_LANDING_PAGE,
                element: <ProtectedRoute>
                    <EntrepreneurLandingPage />
                </ProtectedRoute>
            },
            {
                path: routes.CREATE_ENTERPRISE,
                // element: <ProtectedRoute>
                //     <CreateEnterprisePage/>
                // </ProtectedRoute>
                element: <CreateEnterprisePage />
            },
        ]
    }
])