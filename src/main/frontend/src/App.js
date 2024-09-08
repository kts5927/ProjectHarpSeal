import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
    const [data, setData] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            const response = await axios.get('https://apiprojectharpseal/test');
            setData(response.data);
        };

        fetchData();
    }, []);

    return (
        <div>
            <h1>{data}</h1>
        </div>
    );
}

export default App;
