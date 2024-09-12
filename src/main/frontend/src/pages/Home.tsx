import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Cookies from 'js-cookie'; // js-cookie 라이브러리 추가

function Home() {
    const [data, setData] = useState("");
    const [isAuthenticated, setIsAuthenticated] = useState(false); // 인증 상태 관리

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

        // JWT 쿠키 확인
        const jwtToken = Cookies.get('jwt'); // 쿠키에서 jwt 가져옴
        if (jwtToken) {
            setIsAuthenticated(true); // JWT가 있으면 인증된 상태로 설정
        }

        fetchData();
    }, [API_URL]);

    const handleLogin = () => {
        window.location.href = "https://accounts.google.com/o/oauth2/auth?client_id=595594567479-3ms6j0agmk50rqh85f6g7pph5v0a7mo8.apps.googleusercontent.com&redirect_uri=https://apiprojectharpseal.com/login/oauth2/code/google&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile";
    };

    const handleLogout = () => {
        Cookies.remove('jwt'); // JWT 쿠키 삭제
        setIsAuthenticated(false); // 인증 상태 해제
        window.location.reload(); // 페이지 리로드
    };

    return (
        <div>
            <h1>HarpSeal ver 1.0.0</h1>
            <h1>백엔드 데이터 : {data}</h1>

            {isAuthenticated ? (
                <div>
                    <h2>로그인 상태입니다.</h2>
                    <button onClick={handleLogout}>로그아웃</button>
                </div>
            ) : (
                <button onClick={handleLogin}>Google 로그인</button>
            )}
        </div>
    );
}

export default Home;
