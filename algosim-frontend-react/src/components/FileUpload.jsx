export default function FileUpload({ setJobs }) {

    const handleFile = (event) => {

        const file = event.target.files[0];

        if (!file) return;

        const reader = new FileReader();

        reader.onload = (e) => {

            const lines =
                e.target.result
                    .split("\n")
                    .filter(line => line.trim() !== "");

            const loadedJobs = [];

            lines.forEach(line => {

                const parts = line.split(",");

                loadedJobs.push({
                    processId: parts[0].trim(),
                    arrivalTime: parseInt(parts[1]),
                    burstTime: parseInt(parts[2]),
                    priority: parts[3]
                        ? parseInt(parts[3])
                        : 1
                });

            });

            setJobs(loadedJobs);
        };

        reader.readAsText(file);
    };

    return (
        <input
            type="file"
            accept=".txt,.csv"
            onChange={handleFile}
        />
    );
}