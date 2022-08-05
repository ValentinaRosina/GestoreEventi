import React, { useEffect, useState } from 'react';
import { Evento } from './Evento';
import './toolStyle.css';
import axios from "axios";

export const Eventi = () => {
    const [events, setEvents] = useState();

    useEffect(() => {
        axios.get("http://localhost:8080/eventi/listaEventi").then(
            res => { const responseEvents = res.data; setEvents(responseEvents); }
        )
    }, [])

    return (
        <>
            {events && events.map(ev => {
                const { nome, posti } = ev;
                return (
                    <div key={nome}>
                        <Evento nome={nome} posti={posti} />
                    </div>
                );
            })}
        </>
    );
};
