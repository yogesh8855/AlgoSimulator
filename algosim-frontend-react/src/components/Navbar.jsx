import { Link } from "react-router-dom";

export default function Navbar() {

    return (

        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">

            <div className="container-fluid">

                <Link
                    className="navbar-brand"
                    to="/"
                >
                    AlgoSimulator
                </Link>

                <div className="navbar-nav">

                    <Link
                        className="nav-link"
                        to="/cpu"
                    >
                        CPU
                    </Link>

                    <Link
                        className="nav-link"
                        to="/sorting"
                    >
                        Sorting
                    </Link>

                    <Link
                        className="nav-link"
                        to="/searching"
                    >
                        Searching
                    </Link>

                    <Link
                        className="nav-link"
                        to="/ai-search"
                    >
                        AI Search
                    </Link>

                </div>

            </div>

        </nav>
    );
}