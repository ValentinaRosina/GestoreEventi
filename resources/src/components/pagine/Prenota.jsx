import React, { useState } from 'react';
import './pagineStyle.css';
import axios from "axios";

export const Prenota = () => {


    const [nome, setNome] = useState("");
    const [posti, setPosti] = useState("");

    function updateEvent() {
        var config = {
            headers: {
                'Content-Type': 'text/plain'
            }
        };
        axios.put(`http://localhost:8080/eventi/prenota/${nome}/${posti}`, config).then(res => {
            const responseEvents = res.data;
            console.log({ responseEvents });
        });
    }

    return (
        <>
            <div className='pagetitle'>
                <p>Prenota</p>
            </div>
            <div className="flexible-box">
                <h1>Prenota l'evento a cui vuoi partecipare</h1>
                <form className="inputControls">
                    <div><b>Nome Evento</b>
                        <p><input type="text" onChange={(e) => setNome(e.target.value)} /></p>
                    </div>
                    <div><b>Posti</b>
                        <p><input type="text" onChange={(e) => setPosti(e.target.value)} /></p>
                    </div>
                    <p>
                        <button onClick={updateEvent}>Prenota</button>
                    </p>
                </form>
            </div>
        </>
    );
};
