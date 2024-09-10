import React, { useState, useEffect } from 'react';
import axios from 'axios';

function Home() {
    const [data, setData] = useState("");

    const API_URL = window.location.hostname === 'localhost'
        ? '/api'
        : 'https://apiprojectharpseal.com';

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`${API_URL}/test`);
                setData(response.data);
            } catch (error) {
                console.error("Error fetching data: ", error);
            }
        };

        fetchData();
    }, [API_URL]);

    return (
        <div>
            <h1>HarpSeal ver 1.0.0</h1>
            <h1>백엔드 데이터 : {data}</h1>
        </div>
    );
}

export default Home;
