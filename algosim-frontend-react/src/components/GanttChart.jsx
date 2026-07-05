import { useDarkMode } from "../context/DarkModeContext";

const colors = [
  "bg-blue-500",
  "bg-purple-500",
  "bg-green-500",
  "bg-orange-500",
  "bg-pink-500",
  "bg-cyan-500",
];

export default function GanttChart({ jobs }) {
    const { darkMode } = useDarkMode();

    if (!jobs || jobs.length === 0) {
        return null;
    }

    const processColors = {};
    jobs.forEach((job, index) => {
        if (!processColors[job.processId]) {
            processColors[job.processId] = colors[index % colors.length];
        }
    });

    const totalBurstTime = jobs.reduce((sum, job) => sum + job.burstTime, 0);
    const unitWidth = Math.max(40, 800 / totalBurstTime);

    return (
        <div className={`p-6 rounded-xl border-2 ${darkMode ? 'bg-gray-800 border-gray-700' : 'bg-white border-gray-200'}`}>
            <h4 className={`text-lg font-semibold mb-4 ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                Gantt Chart
            </h4>

            {/* Timeline */}
            <div className="relative mb-6">
                <div className="flex items-center">
                    {jobs.map((job, index) => {
                        const width = Math.max(job.burstTime * unitWidth, 60);
                        const colorClass = processColors[job.processId] || "bg-blue-500";
                        
                        return (
                            <div key={index} className="relative group">
                                <div
                                    className={`${colorClass} text-white font-bold flex items-center justify-center rounded-lg`}
                                    style={{ 
                                        width: `${width}px`, 
                                        height: "50px",
                                        minWidth: "50px"
                                    }}
                                >
                                    {job.processId}
                                </div>
                            </div>
                        );
                    })}
                </div>

                {/* Time Scale */}
                <div className="flex items-center mt-2">
                    {jobs.map((job, index) => {
                        const width = Math.max(job.burstTime * unitWidth, 60);
                        return (
                            <div
                                key={index}
                                className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}
                                style={{ width: `${width}px`, minWidth: "60px" }}
                            >
                                <div className="text-center">{job.startTime || 0}</div>
                            </div>
                        );
                    })}
                    <div className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>
                        <div className="text-center">{totalBurstTime}</div>
                    </div>
                </div>
            </div>

            {/* Legend */}
            <div className={`p-4 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                <h5 className={`font-semibold mb-3 ${darkMode ? 'text-white' : 'text-gray-900'}`}>Process Legend</h5>
                <div className="flex flex-wrap gap-3">
                    {Object.keys(processColors).map((processId) => (
                        <div key={processId} className="flex items-center space-x-2">
                            <div className={`w-4 h-4 rounded ${processColors[processId]}`} />
                            <span className={`text-sm ${darkMode ? 'text-gray-300' : 'text-gray-700'}`}>{processId}</span>
                        </div>
                    ))}
                </div>
            </div>

            {/* Statistics */}
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-4">
                <div className={`p-3 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                    <div className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Total Processes</div>
                    <div className={`text-lg font-bold ${darkMode ? 'text-white' : 'text-gray-900'}`}>{jobs.length}</div>
                </div>
                <div className={`p-3 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                    <div className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Total Time</div>
                    <div className={`text-lg font-bold ${darkMode ? 'text-white' : 'text-gray-900'}`}>{totalBurstTime}</div>
                </div>
                <div className={`p-3 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                    <div className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>Avg Burst Time</div>
                    <div className={`text-lg font-bold ${darkMode ? 'text-white' : 'text-gray-900'}`}>
                        {(totalBurstTime / jobs.length).toFixed(1)}
                    </div>
                </div>
                <div className={`p-3 rounded-lg ${darkMode ? 'bg-gray-700' : 'bg-gray-50'}`}>
                    <div className={`text-xs ${darkMode ? 'text-gray-400' : 'text-gray-600'}`}>CPU Utilization</div>
                    <div className={`text-lg font-bold ${darkMode ? 'text-green-400' : 'text-green-600'}`}>100%</div>
                </div>
            </div>
        </div>
    );
}