import axios from "axios";

export function Axios() {
    const endpoint = 'https://apiprojectharpseal.com/'

    // GET 함수: URL과 쿼리 파라미터만 처리
    async function GET(url: string, params?: object) {
        try {
            // GET 요청 전송 (params 옵션으로 쿼리 파라미터 처리)
            // 예시
            // GET("api/resource", { search: "term", page: 2  ....})

            const response = await axios.get(`${endpoint}${url}`, {
                params: params || {},  // 쿼리 파라미터가 있으면 전달, 없으면 빈 객체
            });

            return response.data;

        } catch (error) {
            console.error('Error fetching data:', error);
            return null;  // 에러 발생 시 null 반환
        }
    }

    return { GET };  // GET 함수 반환
}
