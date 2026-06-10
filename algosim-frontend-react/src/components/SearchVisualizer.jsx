export default function SearchVisualizer({
    step,
    foundIndex
}) {

    if (!step) return null;

    return (
        <div
            style={{
                display: "flex",
                gap: "10px",
                marginTop: "20px",
                flexWrap: "wrap"
            }}
        >
            {step.arraySnapshot.map((value,index)=>{

                let bg = "#ffffff";

                if(index === step.currentIndex)
                    bg = "#ffc107";

                if(index === foundIndex)
                    bg = "#198754";

                return (
                    <div
                        key={index}
                        style={{
                            width: "70px",
                            height: "70px",
                            border: "2px solid black",
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center",
                            fontWeight: "bold",
                            fontSize: "18px",
                            backgroundColor: bg
                        }}
                    >
                        {value}
                    </div>
                );
            })}
        </div>
    );
}