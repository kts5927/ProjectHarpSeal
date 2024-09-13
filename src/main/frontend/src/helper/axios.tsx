import axios from "axios";

export function Axios() {
    const API_URL = window.location.hostname === 'localhost'
        ? '/api'
        : 'https://apiprojectharpseal.com';

    // POST 함수: 데이터는 body로 처리
    async function post(url: string, data?: object) {
        try {
            // POST 요청 전송 (data는 요청 본문으로 전달)
            const response = await axios.post(`${API_URL}${url}`, data || {});  // 본문에 데이터 전달
            return response.data;

        } catch (error) {
            console.error('Error posting data:', error);
            return null;  // 에러 발생 시 null 반환
        }
    }

    // GET 함수: URL에 쿼리 파라미터 전달
    async function get(url: string, params?: object) {
        try {
            const response = await axios.get(`${API_URL}${url}`, {
                params: params || {}  // 쿼리 파라미터 전달
            });
            return response.data;

        } catch (error) {
            console.error('Error getting data:', error);
            return null;  // 에러 발생 시 null 반환
        }
    }

    return { post, get };  // POST 및 GET 함수 반환
}
