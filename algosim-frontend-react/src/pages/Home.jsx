import { Link } from "react-router-dom";

export default function Home() {

    const modules = [
        {
            title: "CPU Scheduling",
            description: "Visualize FCFS, SJF, SRTF, RR and Priority Scheduling",
            path: "/cpu"
        },
        {
            title: "Sorting Algorithms",
            description: "Bubble, Selection and Insertion Sort Visualization",
            path: "/sorting"
        },
        {
            title: "Searching Algorithms",
            description: "Linear and Binary Search Visualization",
            path: "/searching"
        },
        {
            title: "AI Search Algorithms",
            description: "BFS, DFS and A* Path Finding",
            path: "/ai-search"
        }
    ];

    return (
        <div className="container">

            <div className="text-center mt-4 mb-5">
                <h1>AlgoSimulator</h1>

                <p className="lead">
                    Interactive Algorithm Visualization Platform
                </p>
            </div>

            <div className="row">

                {modules.map((module,index)=>(
                    <div className="col-md-6 mb-4" key={index}>

                        <div className="card shadow h-100">

                            <div className="card-body">

                                <h4 className="card-title">
                                    {module.title}
                                </h4>

                                <p className="card-text">
                                    {module.description}
                                </p>

                                <Link
                                    to={module.path}
                                    className="btn btn-primary"
                                >
                                    Open Module
                                </Link>

                            </div>

                        </div>

                    </div>
                ))}

            </div>

        </div>
    );
}