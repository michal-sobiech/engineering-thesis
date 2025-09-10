import { RouterProvider } from "react-router-dom";

import { ChakraProvider } from "@chakra-ui/react";
import { AuthProvider } from "./auth-context/AuthProvider";
import { system } from "./config/system-config";
import { router } from "./router/router";

function App() {
    return <AuthProvider>
        <ChakraProvider value={system}>
            <RouterProvider router={router} />
        </ChakraProvider>
    </AuthProvider>;
}

export default App;
