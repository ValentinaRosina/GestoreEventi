import React from 'react';
import { BrowserRouter } from 'react-router-dom';
import { Switch } from './Switch';

export const App = () => {
    return (
        <BrowserRouter>
            <Switch />
        </BrowserRouter>
    );
};
