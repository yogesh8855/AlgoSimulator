import { useEffect, useState } from "react";
import { runSorting } from "../services/sortingApi";
import SortingVisualizer from "../components/SortingVisualizer";
import ComplexityChart from "../components/ComplexityChart";
import { useTextToSpeech } from "../hooks/useTextToSpeech";
import { useDarkMode } from "../context/DarkModeContext";

export default function Sorting() {
    const { darkMode } = useDarkMode();
    const [algorithm, setAlgorithm] = useState("bubble");
    const [arrayInput, setArrayInput] = useState("64,34,25,12,22,11,90");
    const [response, setResponse] = useState(null);
    const [currentStep, setCurrentStep] = useState(0);
    const [playing, setPlaying] = useState(false);
    const [speed, setSpeed] = useState(1000);
    const [voiceEnabled, setVoiceEnabled] = useState(false);

    const { speak, cancel, isSpeaking, isSupported } = useTextToSpeech();

    const runAlgorithm = async () => {
        try {
            const request = {
                algorithm,
                array: arrayInput.split(",").map(Number)
            };
            const data = await runSorting(request);
            setResponse(data);
            setCurrentStep(0);
            setPlaying(false);
        } catch (error) {
            console.error(error);
            alert("Backend connection failed");
        }
    };

    const nextStep = () => {
        if (response && currentStep < response.steps.length - 1) {
            setCurrentStep(prev => prev + 1);
        }
    };

    const previousStep = () => {
        if (currentStep > 0) {
            setCurrentStep(prev => prev - 1);
        }
    };

    useEffect(() => {
        if (!playing || !response) return;
        const timer = setInterval(() => {
            setCurrentStep(prev => {
                if (prev >= response.steps.length - 1) {
                    setPlaying(false);
                    return prev;
                }
                return prev + 1;
            });
        }, speed);
        return () => clearInterval(timer);
    }, [playing, response, speed]);

    useEffect(() => {
        if (voiceEnabled && response && response.steps[currentStep]?.note) {
            cancel();
            speak(response.steps[currentStep].note);
        }
        return () => {
            if (isSpeaking) {
                cancel();
            }
        };
    }, [currentStep, voiceEnabled, response]);

    const toggleVoice = () => {
        if (voiceEnabled) {
            cancel();
        }
        setVoiceEnabled(!voiceEnabled);
    };

    return (
        <div className="max-w-6xl mx-auto px-4 py-8">
            <h2 className={`text-3xl font-bold mb-6 ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                Sorting Algorithms
            </h2>

            {/* Controls */}
            <div className={`p-6 rounded-xl border-2 mb-6 ${
                darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'
            }`}>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
                    <div>
                        <label className={`block text-sm font-medium mb-2 ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>
                            Algorithm
                        </label>
                        <select
                            className={`w-full p-3 rounded-lg border-2 ${
                                darkMode 
                                    ? 'bg-gray-700 border-gray-600 text-white' 
                                    : 'bg-white border-gray-300 text-gray-900'
                            }`}
                            value={algorithm}
                            onChange={(e) => setAlgorithm(e.target.value)}
                        >
                            <option value="bubble">Bubble Sort</option>
                            <option value="selection">Selection Sort</option>
                            <option value="insertion">Insertion Sort</option>
                            <option value="quick">Quick Sort</option>
                            <option value="merge">Merge Sort</option>
                        </select>
                    </div>

                    <div>
                        <label className={`block text-sm font-medium mb-2 ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>
                            Array (comma-separated)
                        </label>
                        <input
                            className={`w-full p-3 rounded-lg border-2 ${
                                darkMode 
                                    ? 'bg-gray-700 border-gray-600 text-white' 
                                    : 'bg-white border-gray-300 text-gray-900'
                            }`}
                            value={arrayInput}
                            onChange={(e) => setArrayInput(e.target.value)}
                            placeholder="64,34,25,12,22,11,90"
                        />
                    </div>
                </div>

                <button
                    className="w-full bg-blue-500 hover:bg-blue-600 text-white font-medium py-3 px-6 rounded-lg transition-colors"
                    onClick={runAlgorithm}
                >
                    Run Algorithm
                </button>
            </div>

            {response && (
                <>
                    {/* Visualization */}
                    <div className={`p-6 rounded-xl border-2 mb-6 ${
                        darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'
                    }`}>
                        <h3 className={`text-xl font-semibold mb-4 ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                            Visualization
                        </h3>

                        <SortingVisualizer
                            step={{
                                ...response.steps[currentStep],
                                totalSteps: response.steps.length,
                                currentStep: currentStep + 1
                            }}
                        />

                        <p className={`mt-4 text-center ${darkMode ? 'text-gray-300' : 'text-gray-600'}`}>
                            {response.steps[currentStep].note}
                        </p>

                        {/* Controls */}
                        <div className="flex flex-wrap gap-2 mt-4 justify-center">
                            <button
                                className={`px-4 py-2 rounded-lg font-medium ${
                                    darkMode 
                                        ? 'bg-gray-700 hover:bg-gray-600 text-white' 
                                        : 'bg-gray-200 hover:bg-gray-300 text-gray-900'
                                }`}
                                onClick={previousStep}
                            >
                                Previous
                            </button>
                            <button
                                className={`px-4 py-2 rounded-lg font-medium ${
                                    darkMode 
                                        ? 'bg-gray-700 hover:bg-gray-600 text-white' 
                                        : 'bg-gray-200 hover:bg-gray-300 text-gray-900'
                                }`}
                                onClick={nextStep}
                            >
                                Next
                            </button>
                            <button
                                className="px-4 py-2 rounded-lg font-medium bg-green-500 hover:bg-green-600 text-white"
                                onClick={() => setPlaying(true)}
                            >
                                Play
                            </button>
                            <button
                                className="px-4 py-2 rounded-lg font-medium bg-red-500 hover:bg-red-600 text-white"
                                onClick={() => setPlaying(false)}
                            >
                                Pause
                            </button>
                            {isSupported && (
                                <button
                                    className={`px-4 py-2 rounded-lg font-medium ${
                                        voiceEnabled 
                                            ? 'bg-blue-500 text-white' 
                                            : 'bg-gray-200 text-gray-900'
                                    }`}
                                    onClick={toggleVoice}
                                >
                                    {voiceEnabled ? '🔊' : '🔇'}
                                </button>
                            )}
                        </div>

                        {/* Speed Control */}
                        <div className="mt-4">
                            <label className={`block text-sm font-medium mb-2 ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>
                                Speed: {speed}ms
                            </label>
                            <input
                                type="range"
                                min="200"
                                max="2000"
                                step="100"
                                value={speed}
                                onChange={(e) => setSpeed(Number(e.target.value))}
                                className="w-full"
                            />
                        </div>
                    </div>

                    {/* Statistics */}
                    <div className={`p-6 rounded-xl border-2 mb-6 ${
                        darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'
                    }`}>
                        <h3 className={`text-xl font-semibold mb-4 ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                            Statistics
                        </h3>
                        <div className="grid grid-cols-2 gap-4">
                            <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                                <div className={`text-sm ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Comparisons</div>
                                <div className={`text-2xl font-bold ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                                    {response.comparisons}
                                </div>
                            </div>
                            <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                                <div className={`text-sm ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Swaps</div>
                                <div className={`text-2xl font-bold ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                                    {response.swaps}
                                </div>
                            </div>
                        </div>
                    </div>

                    {/* Complexity Chart */}
                    <div className="mt-4">
                        <ComplexityChart algorithm={algorithm} />
                    </div>
                </>
            )}
        </div>
    );
}