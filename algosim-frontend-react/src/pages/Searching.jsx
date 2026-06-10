import { useState } from "react";
import { runSearching } from "../services/searchingApi";
import SearchVisualizer from "../components/SearchVisualizer";

export default function Searching() {

    const [algorithm, setAlgorithm] =
        useState("LINEAR");

    const [arrayInput, setArrayInput] =
        useState("10,20,30,40,50,60");

    const [target, setTarget] =
        useState("50");

    const [response, setResponse] =
        useState(null);

    const [currentStep, setCurrentStep] =
        useState(0);

    const runAlgorithm = async () => {

        try {

            const request = {
                algorithm,
                array: arrayInput
                    .split(",")
                    .map(Number),
                target: parseInt(target)
            };

            const data =
                await runSearching(request);

            setResponse(data);
            setCurrentStep(0);

        } catch (error) {

            console.error(error);
            alert("Backend connection failed");

        }
    };

    const nextStep = () => {

        if (
            response &&
            currentStep < response.steps.length - 1
        ) {
            setCurrentStep(currentStep + 1);
        }

    };

    const previousStep = () => {

        if (currentStep > 0) {
            setCurrentStep(currentStep - 1);
        }

    };

    return (

        <div className="container mt-4">

            <h2>
                Searching Algorithms
            </h2>

            <div className="card shadow p-3">

                <div className="mb-3">

                    <label>
                        Algorithm
                    </label>

                    <select
                        className="form-select"
                        value={algorithm}
                        onChange={(e)=>
                            setAlgorithm(
                                e.target.value
                            )
                        }
                    >
                        <option value="LINEAR">
                            Linear Search
                        </option>

                        <option value="BINARY">
                            Binary Search
                        </option>

                    </select>

                </div>

                <div className="mb-3">

                    <label>
                        Array
                    </label>

                    <input
                        className="form-control"
                        value={arrayInput}
                        onChange={(e)=>
                            setArrayInput(
                                e.target.value
                            )
                        }
                    />

                </div>

                <div className="mb-3">

                    <label>
                        Target
                    </label>

                    <input
                        type="number"
                        className="form-control"
                        value={target}
                        onChange={(e)=>
                            setTarget(
                                e.target.value
                            )
                        }
                    />

                </div>

                <button
                    className="btn btn-primary"
                    onClick={runAlgorithm}
                >
                    Run
                </button>

            </div>

            {
                response &&
                (
                    <div
                        className="card shadow p-3 mt-4"
                    >

                        <h4>
                            Visualization
                        </h4>

                        <SearchVisualizer
                            step={
                                response.steps[
                                    currentStep
                                ]
                            }
                            foundIndex={
                                response.foundIndex
                            }
                        />

                        <p className="mt-3">

                            {
                                response.steps[
                                    currentStep
                                ].description
                            }

                        </p>

                        <div
                            className="d-flex gap-2"
                        >

                            <button
                                className="btn btn-secondary"
                                onClick={
                                    previousStep
                                }
                            >
                                Previous
                            </button>

                            <button
                                className="btn btn-success"
                                onClick={
                                    nextStep
                                }
                            >
                                Step
                            </button>

                        </div>

                    </div>
                )
            }

            {
                response &&
                (
                    <div
                        className="card shadow p-3 mt-4"
                    >

                        <h4>
                            Pseudocode
                        </h4>

                        <pre>

                            {
                                response.pseudocodeLines.join("\n")
                            }

                        </pre>

                    </div>
                )
            }

        </div>

    );
}