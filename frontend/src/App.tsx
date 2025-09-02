// import SignUpEnterPasswordPage from "./pages/sign_up_enter_password/SignUpEnterPasswordPage";


// import SignUpEnterPasswordPage from "./pages/sign_up_enter_password/SignUpEnterPasswordPage";

// import SignUpEnterEmailPage from "./pages/sign_up_enter_email/SignUpChooseUsernamePage";

import { ChakraProvider } from "@chakra-ui/react";
import { system } from "./config/system-config";
import SignUpSuccessPage from "./pages/sign_up_success/SignUpSuccessPage";

function App() {
  // return <SignUpEnterEmailPage />;
  // return <SignUpEnterNamePage />;
  // return <SignUpEnterPasswordPage />;
  return <ChakraProvider value={system}>
    <SignUpSuccessPage />;
  </ChakraProvider>
}

export default App;
