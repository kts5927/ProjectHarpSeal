import { Cookies } from 'react-cookie';

const cookies = new Cookies();

function setCookie(token: string, expireInSeconds: number): void {
    // 만료 시간을 UTC 기준으로 계산
    const expireTimeUTC = Date.now() + expireInSeconds * 1000; // 만료시간을 밀리초로 계산
    const expireTimeKST = expireTimeUTC + (9 * 60 * 60 * 1000); // KST로 변환
    const expireDateKST = new Date(expireTimeKST); // KST 시간으로 변환

    // 이미 전달받은 JWT 토큰을 쿠키에 저장하고, 만료일을 설정합니다.
    cookies.set('jwt', token, {
        expires: expireDateKST,
    });

    console.log('JWT 쿠키가 설정되었습니다.');
}

export default setCookie;
