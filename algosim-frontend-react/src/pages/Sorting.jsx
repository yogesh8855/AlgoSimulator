import { useEffect, useState } from "react";
import { runSorting } from "../services/sortingApi";
import SortingVisualizer from "../components/SortingVisualizer";

export default function Sorting() {

    const [algorithm,setAlgorithm] =
        useState("bubble");

    const [arrayInput,setArrayInput] =
        useState("64,34,25,12,22,11,90");

    const [response,setResponse] =
        useState(null);

    const [currentStep,setCurrentStep] =
        useState(0);

    const [playing,setPlaying] =
        useState(false);

    const [speed,setSpeed] =
        useState(1000);

    const runAlgorithm = async () => {

        try {

            const request = {

                algorithm,

                array: arrayInput
                    .split(",")
                    .map(Number)

            };

            const data =
                await runSorting(request);

            setResponse(data);
            setCurrentStep(0);
            setPlaying(false);

        }
        catch(error){

            console.error(error);
            alert("Backend connection failed");

        }
    };

    const nextStep = () => {

        if(
            response &&
            currentStep <
            response.steps.length - 1
        ){
            setCurrentStep(prev=>prev+1);
        }
    };

    const previousStep = () => {

        if(currentStep > 0){
            setCurrentStep(prev=>prev-1);
        }
    };

    useEffect(()=>{

        if(!playing || !response)
            return;

        const timer = setInterval(()=>{

            setCurrentStep(prev=>{

                if(
                    prev >=
                    response.steps.length - 1
                ){
                    setPlaying(false);
                    return prev;
                }

                return prev + 1;
            });

        },speed);

        return ()=>clearInterval(timer);

    },[playing,response,speed]);

    return (

        <div className="container mt-4">

            <h2>
                Sorting Algorithms
            </h2>

            <div className="card p-3 shadow">

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

                        <option value="bubble">
                            Bubble Sort
                        </option>

                        <option value="selection">
                            Selection Sort
                        </option>

                        <option value="insertion">
                            Insertion Sort
                        </option>

                        <option value="quick">
                            Quick Sort
                        </option>

                        <option value="merge">
                            Merge Sort
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
                        className="card p-3 shadow mt-4"
                    >

                        <h4>
                            Visualization
                        </h4>

                        <SortingVisualizer
                            step={
                                response.steps[
                                    currentStep
                                ]
                            }
                        />

                        <p className="mt-3">

                            {
                                response.steps[
                                    currentStep
                                ].note
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

                            <button
                                className="btn btn-primary"
                                onClick={()=>
                                    setPlaying(true)
                                }
                            >
                                Play
                            </button>

                            <button
                                className="btn btn-danger"
                                onClick={()=>
                                    setPlaying(false)
                                }
                            >
                                Pause
                            </button>

                        </div>

                        <div
                            className="mt-3"
                        >

                            <label>
                                Speed
                            </label>

                            <input
                                type="range"
                                min="200"
                                max="2000"
                                step="100"
                                value={speed}
                                onChange={(e)=>
                                    setSpeed(
                                        Number(
                                            e.target.value
                                        )
                                    )
                                }
                            />

                        </div>

                    </div>
                )
            }

            {
                response &&
                (
                    <div
                        className="card p-3 shadow mt-4"
                    >

                        <h4>
                            Statistics
                        </h4>

                        <p>
                            Comparisons:
                            {" "}
                            {response.comparisons}
                        </p>

                        <p>
                            Swaps:
                            {" "}
                            {response.swaps}
                        </p>

                    </div>
                )
            }

        </div>
    );
}