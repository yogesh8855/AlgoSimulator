import { Link } from "react-router-dom";
import { useDarkMode } from "../context/DarkModeContext";
import { Cpu, BarChart3, Search, Brain } from "lucide-react";

export default function Home() {
    const { darkMode } = useDarkMode();

    const modules = [
        {
            title: "CPU Scheduling",
            description: "FCFS, SJF, SRTF, Round Robin, Priority",
            path: "/cpu",
            icon: Cpu,
            color: "bg-blue-500"
        },
        {
            title: "Sorting Algorithms",
            description: "Bubble, Selection, Insertion, Quick, Merge",
            path: "/sorting",
            icon: BarChart3,
            color: "bg-purple-500"
        },
        {
            title: "Searching Algorithms",
            description: "Linear Search, Binary Search",
            path: "/searching",
            icon: Search,
            color: "bg-green-500"
        },
        {
            title: "AI Search Algorithms",
            description: "BFS, DFS, A* Path Finding",
            path: "/ai-search",
            icon: Brain,
            color: "bg-orange-500"
        }
    ];

    return (
        <div className={`min-h-screen ${
            darkMode ? 'bg-gray-900' : 'bg-gray-50'
        }`}>
            <div className="max-w-6xl mx-auto px-4 py-12">
                
                {/* Hero Section */}
                <div className="text-center mb-12">
                    <h1 className={`text-4xl md:text-5xl font-bold mb-4 ${
                        darkMode ? 'text-white' : 'text-gray-900'
                    }`}>
                        AlgoSimulator
                    </h1>
                    
                    <p className={`text-lg mb-8 max-w-2xl mx-auto ${
                        darkMode ? 'text-gray-400' : 'text-gray-600'
                    }`}>
                        Learn algorithms through interactive visualizations
                    </p>
                </div>

                {/* Module Cards */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    {modules.map((module, index) => {
                        const Icon = module.icon;
                        return (
                            <Link key={index} to={module.path}>
                                <div className={`h-full p-6 rounded-xl border-2 transition-all hover:shadow-lg ${
                                    darkMode
                                        ? 'bg-gray-800 border-gray-700 hover:border-blue-500'
                                        : 'bg-white border-gray-200 hover:border-blue-500'
                                }`}>
                                    <div className={`w-12 h-12 rounded-lg ${module.color} flex items-center justify-center mb-4`}>
                                        <Icon className="w-6 h-6 text-white" />
                                    </div>
                                    
                                    <h3 className={`text-xl font-bold mb-2 ${
                                        darkMode ? 'text-white' : 'text-gray-900'
                                    }`}>
                                        {module.title}
                                    </h3>
                                    
                                    <p className={`${
                                        darkMode ? 'text-gray-400' : 'text-gray-600'
                                    }`}>
                                        {module.description}
                                    </p>
                                </div>
                            </Link>
                        );
                    })}
                </div>

            </div>
        </div>
    );
}