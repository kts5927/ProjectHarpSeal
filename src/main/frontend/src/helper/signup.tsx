import { Axios } from "./axios";

async function Signup(email: string, password: string): Promise<string> {
    const { post } = Axios();

    try {
        const result = await post("/signup/request", {
            email: email,
            password: password
        });
        console.log(result)
        // 성공 또는 실패 결과에 따라 값을 반환
        return result === "success" ? "회원가입 완료" : "이미 존재하는 회원";
    } catch (error) {
        return "오류 발생";
    }
}

export default Signup;
