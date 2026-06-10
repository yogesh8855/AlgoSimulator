export default function ResultTable({ result }) {

    if (!result) return null;

    return (
        <>
            <table border="1">

                <thead>
                <tr>
                    <th>PID</th>
                    <th>Waiting</th>
                    <th>Turnaround</th>
                    <th>Completion</th>
                </tr>
                </thead>

                <tbody>

                {result.jobs.map((job,index)=>(
                    <tr key={index}>
                        <td>{job.processId}</td>
                        <td>{job.waitingTime}</td>
                        <td>{job.turnaroundTime}</td>
                        <td>{job.completionTime}</td>
                    </tr>
                ))}

                </tbody>

            </table>

            <h4>
                Average Waiting Time:
                {" "}
                {result.averageWaitingTime.toFixed(2)}
            </h4>

            <h4>
                Average Turnaround Time:
                {" "}
                {result.averageTurnaroundTime.toFixed(2)}
            </h4>

        </>
    );
}