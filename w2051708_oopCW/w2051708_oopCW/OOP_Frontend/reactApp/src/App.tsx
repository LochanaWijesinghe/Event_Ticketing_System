import { BrowserRouter, Router, Route, Routes } from "react-router-dom";
import VendorPage from "./pages/VendorPage";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route index element={<VendorPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
