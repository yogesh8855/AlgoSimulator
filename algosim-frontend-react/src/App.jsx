import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Navbar from "./components/Navbar";

import Home from "./pages/Home";
import CpuScheduling from "./pages/CpuScheduling";
import Sorting from "./pages/Sorting";
import Searching from "./pages/Searching";
import AISearch from "./pages/AISearch";

function App() {

    return (
        <BrowserRouter>

            <Navbar />

            <div style={{padding:"20px",
                         width: "100vw",
                         minHeight: "100vh"}}>

                <Routes>

                    <Route
                        path="/"
                        element={<Home />}
                    />

                    <Route
                        path="/cpu"
                        element={<CpuScheduling />}
                    />

                    <Route
                        path="/sorting"
                        element={<Sorting />}
                    />

                    <Route
                        path="/searching"
                        element={<Searching />}
                    />

                    <Route
                        path="/ai-search"
                        element={<AISearch />}
                    />

                </Routes>

            </div>

        </BrowserRouter>
    );
}

export default App;