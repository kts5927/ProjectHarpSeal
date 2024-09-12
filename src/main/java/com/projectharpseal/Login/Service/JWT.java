package com.projectharpseal.Login.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Service
public class JWT {

    private final SecretKey SECRET_KEY;

    // Environment 객체를 통해 환경 변수에서 값을 가져옴
    public JWT(Environment env) {
        // 비밀 키를 생성 (byte[] -> SecretKey로 변환)
        // SECRET_KEY는 32바이트 이상이어야함
        String secretKeyString = Objects.requireNonNull(env.getProperty("secretkey"),
                "secretkey null error");
        this.SECRET_KEY = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // JWT 토큰 생성
    public String generateToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)  // 사용자 정의 claims 설정
                .setSubject(email)   // 주체 설정
                .setIssuedAt(new Date())  // 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + 10800000))  // 만료 시간
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)  // key값 설정
                .compact();
    }

    // JWT 토큰 검증
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // 비밀 키 설정
                .build()
                .parseClaimsJws(token)  // 토큰 파싱 및 검증
                .getBody();
    }
}
