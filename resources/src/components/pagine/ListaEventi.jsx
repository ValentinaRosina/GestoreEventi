import React from 'react';
import { Eventi } from './tool/Eventi';
import './pagineStyle.css';

export const ListaEventi = () => {
    return (
        <>
            <div className='pagetitle'>
                <p>ListaEventi</p>
            </div>
            <div className="listaEventi">
                <Eventi />
            </div>
        </>
    );
};
