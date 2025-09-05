// import SignUpEnterPasswordPage from "./pages/sign_up_enter_password/SignUpEnterPasswordPage";

import { BrowserRouter, Route, Routes } from "react-router-dom";
// import SignUpEnterPasswordPage from "./pages/sign_up_enter_password/SignUpEnterPasswordPage";

// import SignUpEnterEmailPage from "./pages/sign_up_enter_email/SignUpChooseUsernamePage";

import { ChakraProvider } from "@chakra-ui/react";
import { system } from "./config/system-config";
import LogInPage from "./pages/log_in/LogInPage";
import SignUpSuccessPage from "./pages/sign_up_success/SignUpSuccessPage";
// import SignUpSuccessPage from "./pages/sign_up_success/SignUpSuccessPage";

function App() {
  // return <SignUpEnterEmailPage />;
  // return <SignUpEnterNamePage />;
  // return <SignUpEnterPasswordPage />;
  return <BrowserRouter>
    <ChakraProvider value={system}>
      {/* <SignUpSuccessPage />; */}
      <nav>

      </nav>
      <Routes>
        <Route path="/" element={<LogInPage />} />
        <Route path="/success" element={<SignUpSuccessPage />} />
      </Routes>
      <LogInPage />
    </ChakraProvider>
  </BrowserRouter>
}

export default App;
