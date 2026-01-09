function BackgroundGrid() {


    return (
        <div className="fixed inset-0 opacity-10 pointer-events-none">
            <div className="absolute inset-0" style={{
                backgroundImage: `
            linear-gradient(to right, #10b981 1px, transparent 1px),
            linear-gradient(to bottom, #10b981 1px, transparent 1px)
          `,
                backgroundSize: '40px 40px',
                animation: 'grid-move 20s linear infinite'
            }}></div>
        </div>
    )
}

export default BackgroundGrid;

