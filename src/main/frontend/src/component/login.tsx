import React, { useState } from "react";

function Login() {
    const [user, setUser] = useState({ name: '', id: '', email: ''});

    const handleSubmit = (event: React.FormEvent) => {
        // 기본 동작 방지
        event.preventDefault();
        // 로그인 처리 로직

    };

    return (
        <div>
            <h1>로그인</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>이름: {user.name}</label>
                </div>
                <div>
                    <label>이메일: {user.email}</label>
                </div>
                <button type="submit">회원가입</button>
            </form>
        </div>
    );
}

export default Login;
