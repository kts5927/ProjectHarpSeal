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
    const handleLogin = () => {
        window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id=595594567479-3ms6j0agmk50rqh85f6g7pph5v0a7mo8.apps.googleusercontent.com&redirect_uri=http://localhost:8082/login/oauth2/code/google&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    };




    return (
        <div>
            <h1>HarpSeal ver 1.0.0</h1>
            <h1>백엔드 데이터 : {data}</h1>

            <button onClick={handleLogin}>Google 로그인</button>

        </div>
    );
}

export default Home;
