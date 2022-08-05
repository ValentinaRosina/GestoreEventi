import React from 'react';
import './toolStyle.css';

export const Evento = (props) => {
    return (
        <div className='evento'>
            <p><b>Nome:</b> {props.nome} </p>
            <p><b>Posti disponibili:</b> {props.posti} </p>
        </div>
    );
};
