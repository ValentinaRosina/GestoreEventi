import React from 'react';
import { Routes, Route } from 'react-router-dom';
import { Header } from '../components/layout/Header';
import { Home } from '../components/pagine/Home.jsx';
import { Footer } from '../components/layout/Footer';
import { ListaEventi } from '../components/pagine/ListaEventi'
import { Prenota } from '../components/pagine/Prenota'

export const Switch = () => {
    return (
        <>
            <Header />
            <Routes>
                <Route exact path="/" element={<Home />} />
                <Route exact path="/listaEventi" element={<ListaEventi />} />
                <Route exact path="/prenota" element={<Prenota />} />
            </Routes>
            <Footer />
        </>
    );
};
