export default function GridVisualizer({

    rows,
    cols,
    currentStep,
    start,
    goal,
    blockedCells

}) {

    const blockedSet = new Set(
        blockedCells.map(
            cell => `${cell[0]}-${cell[1]}`
        )
    );

    return (

        <div
            style={{
                display: "grid",
                gridTemplateColumns: `repeat(${cols},40px)`,
                gap: "2px",
                marginTop: "20px"
            }}
        >

            {Array.from({ length: rows * cols }).map((_, index) => {

                const row = Math.floor(index / cols);
                const col = index % cols;

                let color = "#ffffff";

                if (
                    blockedSet.has(`${row}-${col}`)
                ) {
                    color = "#343a40";
                }

                if (
                    row === start[0] &&
                    col === start[1]
                ) {
                    color = "#198754";
                }

                if (
                    row === goal[0] &&
                    col === goal[1]
                ) {
                    color = "#dc3545";
                }

                if (
                    currentStep &&
                    row === currentStep.row &&
                    col === currentStep.col
                ) {

                    if (
                        currentStep.type === "open"
                    ) {
                        color = "#0dcaf0";
                    }

                    if (
                        currentStep.type === "close"
                    ) {
                        color = "#ffc107";
                    }

                    if (
                        currentStep.type === "path"
                    ) {
                        color = "#6f42c1";
                    }
                }

                return (

                    <div
                        key={index}
                        style={{
                            width: "40px",
                            height: "40px",
                            border: "1px solid #ccc",
                            backgroundColor: color
                        }}
                    />

                );

            })}

        </div>

    );
}