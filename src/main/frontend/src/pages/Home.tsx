import React, { useState, useEffect } from 'react';
import {Axios} from "../helper/axios";
import Cookies from 'js-cookie'; // js-cookie 라이브러리 추가
import {useNavigate} from "react-router-dom";

function Home() {
    const [data, setData] = useState("");
    const [isAuthenticated, setIsAuthenticated] = useState(false); // 인증 상태 관리
    const navigate = useNavigate(); // useNavigate 훅 호출

    const { get, post } = Axios();

    const API_parameter = window.location.hostname === 'localhost'
        ? 'http://localhost:8082'
        : 'https://apiprojectharpseal.com';

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await get(`/test`);
                setData(response); // 상태 업데이트
            } catch (error) {
                console.error("Error fetching data: ", error);
            }
        };

        fetchData();
    }, [ get, post, navigate ]);

    const handleLogin = () => {
        const jwtToken = Cookies.get('jwt'); // 쿠키에서 jwt 가져옴
        console.log(jwtToken)
        // JWT 쿠키 확인
        if (jwtToken) {
            const verifyToken = async () => {
                try {
                    // JWT를 쿼리 파라미터로 보내는 예시
                    const response = await post("/JWT/verify", { jwt: jwtToken });
                    console.log("Response = ",response)
                    if (response === "Login successful") {
                        setIsAuthenticated(true);
                    } else if (response === "Login failed") {
                        navigate("/login"); // 로그인 페이지로 이동
                    } else {
                        alert("로그인 오류 발생");
                        navigate("/");
                    }
                } catch (error) {
                    console.error("요청 실패:", error);
                }
            };

            verifyToken();
        }
else {
            window.location.href = `https://accounts.google.com/o/oauth2/auth?client_id=595594567479-3ms6j0agmk50rqh85f6g7pph5v0a7mo8.apps.googleusercontent.com&redirect_uri=${API_parameter}/login/oauth2/code/google&response_type=code&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile`;
        }
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
