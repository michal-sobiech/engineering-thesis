import { createBrowserRouter } from "react-router-dom";
import LogInPage from "../pages/log_in/LogInPage";
import { SignUpWizard } from "../pages/sign_up/wizard/SignUpWizard";
import { PageLayout } from "./PageLayout";

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
                path: "sign-up",
                element: <SignUpWizard />
            }
        ]
    }
])