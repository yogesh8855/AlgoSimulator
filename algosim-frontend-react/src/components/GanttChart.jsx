export default function GanttChart({ jobs }) {

    if (!jobs || jobs.length === 0) {
        return null;
    }

    return (
        <div
            style={{
                display: "flex",
                alignItems: "center",
                marginTop: "20px",
                flexWrap: "wrap"
            }}
        >
            {jobs.map((job,index)=>{

                const width =
                    Math.max(job.burstTime * 40, 60);

                return (
                    <div
                        key={index}
                        style={{
                            width: `${width}px`,
                            height: "60px",
                            border: "1px solid black",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            backgroundColor: "#0d6efd",
                            color: "white",
                            fontWeight: "bold"
                        }}
                    >
                        {job.processId}
                    </div>
                );
            })}
        </div>
    );
}