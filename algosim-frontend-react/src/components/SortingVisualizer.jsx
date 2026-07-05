export default function SortingVisualizer({ step }) {
    if (!step || !step.snapshot) {
        return null;
    }

    const highlightIndices = step.indices || [];
    const maxValue = Math.max(...step.snapshot);
    const totalSteps = step.totalSteps || 1;
    const currentStep = step.currentStep || 0;
    const progress = (currentStep / totalSteps) * 100;

    const getBarColor = (index) => {
        if (!highlightIndices.includes(index)) {
            return "bg-blue-500";
        }

        switch (step.action) {
            case "swap":
                return "bg-red-500";
            case "compare":
                return "bg-yellow-500";
            case "markSorted":
                return "bg-green-500";
            case "selectPivot":
            case "selectKey":
                return "bg-purple-500";
            default:
                return "bg-cyan-500";
        }
    };

    return (
        <div className="space-y-4">
            {/* Progress Bar */}
            <div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-2">
                <div 
                    className="h-full bg-blue-500 rounded-full transition-all duration-300"
                    style={{ width: `${progress}%` }}
                />
            </div>
            
            <div className="flex justify-between text-sm text-gray-600 dark:text-gray-400">
                <span>Step {currentStep} of {totalSteps}</span>
                <span>{progress.toFixed(0)}% Complete</span>
            </div>

            {/* Visualization */}
            <div className="flex items-end justify-center gap-2 h-64 p-4 rounded-lg border-2 border-gray-200 dark:border-gray-700 bg-gray-50 dark:bg-gray-800 overflow-x-auto">
                {step.snapshot.map((value, index) => {
                    const barHeight = maxValue === 0 ? 20 : Math.max((value / maxValue) * 200, 20);
                    const colorClass = getBarColor(index);

                    return (
                        <div key={index} className="flex flex-col items-center">
                            <div
                                className={`w-8 ${colorClass} rounded-t flex items-center justify-start pt-1 text-white font-bold text-xs`}
                                style={{ height: `${barHeight}px`, minHeight: "20px" }}
                            >
                                {value}
                            </div>
                            <span className="text-xs text-gray-600 dark:text-gray-400 mt-1">
                                {index}
                            </span>
                        </div>
                    );
                })}
            </div>

            {/* Legend */}
            <div className="flex flex-wrap justify-center gap-4 text-xs">
                <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 bg-blue-500 rounded" />
                    <span className="text-gray-600 dark:text-gray-400">Default</span>
                </div>
                <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 bg-yellow-500 rounded" />
                    <span className="text-gray-600 dark:text-gray-400">Compare</span>
                </div>
                <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 bg-red-500 rounded" />
                    <span className="text-gray-600 dark:text-gray-400">Swap</span>
                </div>
                <div className="flex items-center space-x-1">
                    <div className="w-3 h-3 bg-green-500 rounded" />
                    <span className="text-gray-600 dark:text-gray-400">Sorted</span>
                </div>
            </div>
        </div>
    );
}