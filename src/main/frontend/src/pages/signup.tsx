import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import signup from "../helper/signup";  // signup 함수 import
import {Axios} from "../helper/axios";
import cookie from "../helper/cookie";


function Signup() {
    const location = useLocation();
    const navigate = useNavigate();
    const [user, setUser] = useState({ id: '', email: '', jwt: '', password: '' });

    useEffect(() => {
        const jwtToken = Cookies.get('jwt');
        if (jwtToken) {
            const { post } = Axios();

            post("/JWT/email", { jwt: jwtToken })
                .then(response => {
                    console.log(response)
                    // 상태 업데이트
                    setUser(prevState => ({
                        ...prevState,
                        email: response  // 서버 응답으로 email 설정
                    }));
                })
                .catch(error => {
                    console.error("이메일 요청 중 오류 발생:", error);
                });
        }
    }, [location, navigate]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setUser(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (event: React.FormEvent) => {
        // 기본 동작 방지
        event.preventDefault();

        try {
            // 외부 signup 함수 호출
            const result = await signup(user.email, user.password);

            // 결과에 따라 처리
            if (result === "회원가입 완료") {
                alert("회원가입이 성공적으로 완료되었습니다.");
                Cookies.remove("jwt")
                navigate("/");  // 회원가입 성공 시 메인 페이지로 이동
            }
            if (result === "이미 존재하는 회원"){
                alert("이미 존재하는 회원입니다.")
                navigate("/");
            }
            if (result === "오류 발생"){
                alert("오류 발생")
            }
        } catch (error) {
            console.error("회원가입 요청 중 오류 발생:", error);
            alert("회원가입 요청 중 오류가 발생했습니다.");
        }
    };

    return (
        <div>
            <h1>회원가입</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>이메일: {user.email}</label>
                </div>
                <div>
                    <label>비밀번호: </label>
                    <input
                        type="password"
                        name="password"
                        value={user.password}
                        onChange={handleChange}
                        required
                    />
                </div>
                <button type="submit">회원가입</button>
            </form>
        </div>
    );
}

export default Signup;
