export default function ProcessTable({ jobs, setJobs }) {

    const updateField = (index, field, value) => {
        const copy = [...jobs];
        copy[index][field] = value;
        setJobs(copy);
    };

    return (
        <table border="1">
            <thead>
            <tr>
                <th>PID</th>
                <th>Arrival</th>
                <th>Burst</th>
                <th>Priority</th>
            </tr>
            </thead>

            <tbody>
            {jobs.map((job,index)=>(
                <tr key={index}>
                    <td>{job.processId}</td>

                    <td>
                        <input
                            type="number"
                            value={job.arrivalTime}
                            onChange={(e)=>
                                updateField(
                                    index,
                                    "arrivalTime",
                                    parseInt(e.target.value)
                                )
                            }
                        />
                    </td>

                    <td>
                        <input
                            type="number"
                            value={job.burstTime}
                            onChange={(e)=>
                                updateField(
                                    index,
                                    "burstTime",
                                    parseInt(e.target.value)
                                )
                            }
                        />
                    </td>

                    <td>
                        <input
                            type="number"
                            value={job.priority}
                            onChange={(e)=>
                                updateField(
                                    index,
                                    "priority",
                                    parseInt(e.target.value)
                                )
                            }
                        />
                    </td>

                </tr>
            ))}
            </tbody>
        </table>
    );
}