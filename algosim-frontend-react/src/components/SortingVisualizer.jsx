export default function SortingVisualizer({ step }) {

    if (!step || !step.snapshot) {
        return null;
    }

    const highlightIndices = step.indices || [];

    const maxValue = Math.max(...step.snapshot);

    return (

        <div
            style={{
                display: "flex",
                alignItems: "flex-end",
                justifyContent: "center",
                gap: "10px",
                height: "320px",
                minHeight: "320px",
                overflow: "hidden",
                marginTop: "20px",
                padding: "20px",
                border: "1px solid #ddd",
                borderRadius: "8px",
                backgroundColor: "#f8f9fa"
            }}
        >

            {step.snapshot.map((value, index) => {

                let background = "#0d6efd";

                if (highlightIndices.includes(index)) {

                    if (step.action === "swap")
                        background = "#dc3545";

                    else if (step.action === "compare")
                        background = "#ffc107";

                    else if (step.action === "markSorted")
                        background = "#198754";

                    else if (
                        step.action === "selectPivot" ||
                        step.action === "selectKey"
                    )
                        background = "#6f42c1";

                    else
                        background = "#0dcaf0";
                }

                const barHeight =
                    maxValue === 0
                        ? 20
                        : Math.max(
                            (value / maxValue) * 250,
                            20
                        );

                return (

                    <div
                        key={index}
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            alignItems: "center"
                        }}
                    >

                        <div
                            style={{
                                width: "50px",
                                height: `${barHeight}px`,
                                backgroundColor: background,
                                transition: "all 0.5s ease",
                                borderRadius: "4px 4px 0 0",
                                display: "flex",
                                justifyContent: "center",
                                alignItems: "flex-start",
                                color: "white",
                                fontWeight: "bold",
                                paddingTop: "5px"
                            }}
                        >
                            {value}
                        </div>

                        <strong style={{ marginTop: "5px" }}>
                            {index}
                        </strong>

                    </div>

                );

            })}

        </div>

    );
}