import React from 'react';
import { Eventi } from './tool/Eventi';
import useToken from '../tools/useToken';
import { Login } from './Login';

import './pagineStyle.css';
export const ListaEventi = () => {
    const { token, setToken } = useToken();

    if (!token) {
        return <Login setToken={setToken} />
    }
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
