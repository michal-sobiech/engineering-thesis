import { RouterProvider } from "react-router-dom";

import { ChakraProvider } from "@chakra-ui/react";
import { system } from "./config/system-config";
import { router } from "./router/router";

function App() {
    return <ChakraProvider value={system}>
        <RouterProvider router={router} />
    </ChakraProvider>;
}

export default App;
