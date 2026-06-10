import { useState } from "react";
import { runScheduler } from "../services/schedulerApi";
import ProcessTable from "../components/ProcessTable";
import ResultTable from "../components/ResultTable";
import FileUpload from "../components/FileUpload";
import GanttChart from "../components/GanttChart";

export default function CpuScheduling() {

    const [algorithm, setAlgorithm] = useState("FCFS");
    const [timeQuantum, setTimeQuantum] = useState(2);

    const [jobs, setJobs] = useState([
        {
            processId: "P1",
            arrivalTime: 0,
            burstTime: 5,
            priority: 1
        }
    ]);

    const [result, setResult] = useState(null);

    const addProcess = () => {
        setJobs([
            ...jobs,
            {
                processId: `P${jobs.length + 1}`,
                arrivalTime: 0,
                burstTime: 1,
                priority: 1
            }
        ]);
    };

    const handleRun = async () => {
        try {

            const request = {
                algorithm,
                timeQuantum,
                jobs
            };

            const response = await runScheduler(request);

            setResult(response);

        } catch (error) {

            console.error(error);
            alert("Backend connection failed");

        }
    };

    return (
        <div className="container mt-4">

            <div className="card shadow mb-4">

                <div className="card-body">

                    <h2 className="card-title">
                        CPU Scheduling Simulator
                    </h2>

                    <hr />

                    <div
                        className="d-flex gap-3 align-items-center flex-wrap"
                    >

                        <label>
                            Algorithm:
                        </label>

                        <select
                            className="form-select"
                            style={{ width: "220px" }}
                            value={algorithm}
                            onChange={(e) =>
                                setAlgorithm(e.target.value)
                            }
                        >
                            <option value="FCFS">
                                FCFS
                            </option>

                            <option value="SJF">
                                SJF
                            </option>

                            <option value="SRTF">
                                SRTF
                            </option>

                            <option value="Round Robin">
                                Round Robin
                            </option>

                            <option value="Priority">
                                Priority
                            </option>

                            <option value="Priority-Preemptive">
                                Priority Preemptive
                            </option>
                        </select>

                        {
                            algorithm === "Round Robin" &&
                            (
                                <>
                                    <label>
                                        Time Quantum:
                                    </label>

                                    <input
                                        type="number"
                                        min="1"
                                        className="form-control"
                                        style={{ width: "100px" }}
                                        value={timeQuantum}
                                        onChange={(e) =>
                                            setTimeQuantum(
                                                parseInt(e.target.value) || 1
                                            )
                                        }
                                    />
                                </>
                            )
                        }

                        <button
                            className="btn btn-success"
                            onClick={addProcess}
                        >
                            Add Process
                        </button>

                    </div>

                </div>

            </div>

            <div className="card shadow mb-4">

                <div className="card-body">

                    <h4>
                        Import Processes
                    </h4>

                    <FileUpload
                        setJobs={setJobs}
                    />

                </div>

            </div>

            <div className="card shadow mb-4">

                <div className="card-body">

                    <h4>
                        Process Table
                    </h4>

                    <ProcessTable
                        jobs={jobs}
                        setJobs={setJobs}
                    />

                </div>

            </div>

            <button
                className="btn btn-primary btn-lg"
                onClick={handleRun}
            >
                Run Algorithm
            </button>

            {
                result &&
                (
                    <>
                        <div className="card shadow mt-4">

                            <div className="card-body">

                                <h4>
                                    Gantt Chart
                                </h4>

                                <GanttChart
                                    jobs={result.jobs}
                                />

                            </div>

                        </div>

                        <div className="card shadow mt-4">

                            <div className="card-body">

                                <h4>
                                    Scheduling Results
                                </h4>

                                <ResultTable
                                    result={result}
                                />

                            </div>

                        </div>

                    </>
                )
            }

        </div>
    );
}