import { useEffect, useState } from "react";
import { runSearch } from "../services/aiSearchApi";
import GridVisualizer from "../components/GridVisualizer";

export default function AISearch() {

    const [algorithm,setAlgorithm] =
        useState("bfs");

    const [rows] = useState(10);
    const [cols] = useState(10);

    const [response,setResponse] =
        useState(null);

    const [stepIndex,setStepIndex] =
        useState(0);

    const [playing,setPlaying] =
        useState(false);

    const [speed,setSpeed] =
        useState(1000);

    const start = [0,0];

    const goal = [9,9];

    const blockedCells = [
        [2,2],
        [2,3],
        [2,4],
        [5,5],
        [6,5]
    ];

    const handleRun = async () => {

        const request = {

            algorithm,

            rows,

            cols,

            start,

            goal,

            blockedCells

        };

        try {

            const data =
                await runSearch(request);

            setResponse(data);

            setStepIndex(0);

            setPlaying(false);

        }
        catch(error){

            console.error(error);

            alert(
                "Backend connection failed"
            );

        }
    };

    useEffect(()=>{

        if(!playing || !response)
            return;

        const timer = setInterval(()=>{

            setStepIndex(prev=>{

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

    const nextStep = () => {

        if(
            response &&
            stepIndex <
            response.steps.length - 1
        ){
            setStepIndex(prev=>prev+1);
        }
    };

    return (

        <div
            style={{
                padding:"20px"
            }}
        >

            <h2>
                AI Search Visualization
            </h2>

            <select
                value={algorithm}
                onChange={(e)=>
                    setAlgorithm(
                        e.target.value
                    )
                }
            >

                <option value="bfs">
                    BFS
                </option>

                <option value="dfs">
                    DFS
                </option>

                <option value="astar">
                    A*
                </option>

            </select>

            <button
                onClick={handleRun}
                style={{
                    marginLeft:"10px"
                }}
            >
                Run
            </button>

            <button
                onClick={()=>
                    setPlaying(true)
                }
                style={{
                    marginLeft:"10px"
                }}
            >
                Play
            </button>

            <button
                onClick={()=>
                    setPlaying(false)
                }
                style={{
                    marginLeft:"10px"
                }}
            >
                Pause
            </button>

            <button
                onClick={nextStep}
                style={{
                    marginLeft:"10px"
                }}
            >
                Step
            </button>

            <br/><br/>

            <input
                type="range"
                min="200"
                max="2000"
                value={speed}
                onChange={(e)=>
                    setSpeed(
                        Number(
                            e.target.value
                        )
                    )
                }
            />

            {
                response &&
                (
                    <>
                        <GridVisualizer

                            rows={rows}
                            cols={cols}

                            start={start}
                            goal={goal}

                            blockedCells={
                                blockedCells
                            }

                            currentStep={
                                response.steps[
                                    stepIndex
                                ]
                            }

                        />

                        <div
                            style={{
                                marginTop:"20px"
                            }}
                        >

                            <h4>
                                Current Step
                            </h4>

                            <p>
                                {
                                    response.steps[
                                        stepIndex
                                    ]?.note
                                }
                            </p>

                            <p>
                                Explored:
                                {" "}
                                {
                                    response.exploredCount
                                }
                            </p>

                            <p>
                                Opened:
                                {" "}
                                {
                                    response.nodesOpened
                                }
                            </p>

                            <p>
                                Closed:
                                {" "}
                                {
                                    response.nodesClosed
                                }
                            </p>

                            <p>
                                Frontier Peak:
                                {" "}
                                {
                                    response.frontierPeakSize
                                }
                            </p>

                            <p>
                                Path Found:
                                {" "}
                                {
                                    response.found
                                        ? "Yes"
                                        : "No"
                                }
                            </p>

                        </div>
                    </>
                )
            }

        </div>
    );
}