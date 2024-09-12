import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import Cookies from 'js-cookie';
import signup from "../helper/signup";  // signup 함수 import

function Signup() {
    const location = useLocation();
    const navigate = useNavigate();
    const [user, setUser] = useState({ id: '', email: '', jwt: '', password: '' });

    useEffect(() => {
        // URL 파라미터 추출
        const params = new URLSearchParams(location.search);
        const id = params.get('id');
        const email = params.get('email');
        const jwt = params.get('jwt');

        if (id && email && jwt) {
            // 파라미터 값 설정
            setUser({
                id: decodeURIComponent(id),
                email: decodeURIComponent(email),
                jwt: decodeURIComponent(jwt),
                password: ''
            });

            if (jwt !== 'NoJWT') {
                // JWT가 있으면 쿠키에 저장
                Cookies.set('jwt', jwt, { expires: 1 });  // JWT를 쿠키에 저장, 만료일은 1일로 설정
                console.log('JWT 쿠키가 설정되었습니다.');
                window.location.href = "/";
            }
        } else {
            // 필수 파라미터가 없을 경우 처리
            alert('데이터를 받아오는데 실패하였습니다.');
            window.location.href = "/";
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
            const result = await signup(user.id, user.email, user.password);

            // 결과에 따라 처리
            if (result === "회원가입 완료") {
                alert("회원가입이 성공적으로 완료되었습니다.");
                navigate("/");  // 회원가입 성공 시 메인 페이지로 이동
            } else {
                alert("회원가입 중 오류가 발생했습니다.");
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
