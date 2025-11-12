import { RouterProvider } from "react-router-dom";

import { ChakraProvider } from "@chakra-ui/react";
import { system } from "./config/system-config";
import { AppProviders } from "./providers/AppProviders";
import { router } from "./router/router";

function App() {
    return <AppProviders>
        <ChakraProvider value={system}>
            <RouterProvider router={router} />
        </ChakraProvider>
    </AppProviders>;
}

export default App;
