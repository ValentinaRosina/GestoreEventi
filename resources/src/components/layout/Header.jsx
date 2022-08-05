import { Link } from "react-router-dom";
import "./layoutStyle.css";

export const Header = () => {
    return (
        <div className="header">
            <Link to="/">Home</Link>
            <Link to="/listaEventi">ListaEventi</Link>
            <Link to="/prenota">Prenota</Link>
        </div>


    );
};