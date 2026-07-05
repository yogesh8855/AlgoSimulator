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
import { useDarkMode } from "./context/DarkModeContext";

function App() {
    const { darkMode } = useDarkMode();

    return (
        <BrowserRouter>

            <Navbar />

            <div className={`min-h-screen transition-colors duration-300 ${
                darkMode ? 'bg-gray-900' : 'bg-gray-50'
            }`}>
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