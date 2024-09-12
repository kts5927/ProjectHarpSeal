import { Axios } from "./axios";

async function Signup(id: string, email: string, password: string): Promise<string> {
    const { GET } = Axios();

    try {
        const result = await GET("signup/request", {
            id: id,
            email: email,
            password: password
        });

        // 성공 또는 실패 결과에 따라 값을 반환
        return result === "success" ? "회원가입 완료" : "회원가입 실패";
    } catch (error) {
        console.error("회원가입 요청 중 오류 발생:", error);
        return "오류 발생";
    }
}

export default Signup;
