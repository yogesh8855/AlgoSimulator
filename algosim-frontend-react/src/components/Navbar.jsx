import { Link } from "react-router-dom";
import { useDarkMode } from "../context/DarkModeContext";
import { Moon, Sun } from "lucide-react";

export default function Navbar() {
    const { darkMode, toggleDarkMode } = useDarkMode();

    const navItems = [
        { path: "/cpu", label: "CPU" },
        { path: "/sorting", label: "Sorting" },
        { path: "/searching", label: "Searching" },
        { path: "/ai-search", label: "AI Search" },
    ];

    return (
        <nav className={`sticky top-0 z-50 border-b ${
            darkMode 
                ? 'bg-gray-900 border-gray-700' 
                : 'bg-white border-gray-200'
        }`}>
            <div className="max-w-6xl mx-auto px-4">
                <div className="flex items-center justify-between h-16">
                    <Link
                        to="/"
                        className={`text-xl font-bold ${
                            darkMode ? 'text-white' : 'text-gray-900'
                        }`}
                    >
                        AlgoSimulator
                    </Link>

                    <div className="flex items-center space-x-4">
                        {navItems.map((item) => (
                            <Link
                                key={item.path}
                                to={item.path}
                                className={`text-sm font-medium transition-colors ${
                                    darkMode
                                        ? 'text-gray-300 hover:text-white'
                                        : 'text-gray-700 hover:text-gray-900'
                                }`}
                            >
                                {item.label}
                            </Link>
                        ))}
                        
                        <button
                            onClick={toggleDarkMode}
                            className={`p-2 rounded-lg ${
                                darkMode
                                    ? 'text-gray-300 hover:text-white hover:bg-gray-800'
                                    : 'text-gray-700 hover:text-gray-900 hover:bg-gray-100'
                            }`}
                            aria-label="Toggle dark mode"
                        >
                            {darkMode ? <Sun className="w-5 h-5" /> : <Moon className="w-5 h-5" />}
                        </button>
                    </div>
                </div>
            </div>
        </nav>
    );
}