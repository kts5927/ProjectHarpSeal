package com.projectharpseal.Login.Controller;

import com.projectharpseal.Login.Service.JWT;
import com.projectharpseal.Login.Service.SignupService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/JWT", produces = "application/json")
public class JwtController {

    private static final Logger logger = LoggerFactory.getLogger(SignupService.class);
    private final JWT jwtService;

    public JwtController(JWT jwtService) {
        this.jwtService = jwtService;
    }

    // JWT 검증 엔드포인트 (POST 요청의 본문에서 JWT를 받음)
    @PostMapping("/verify")
    public String verify(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("jwt");
        try {
            // JWT 토큰 검증
            Claims claims = jwtService.validateToken(token);
            String tf = claims.get("TF", String.class);
            logger.info("토큰 검증 결과 = {}", tf);

            if (tf.equals("True")) {
                return "Login successful";
            } else {
                return "Login failed";
            }
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }

    // 이메일 반환 엔드포인트
    @PostMapping("/email")
    public String email(@RequestBody Map<String, String> requestBody) {
        String jwt = requestBody.get("jwt");
        try {
            // JWT 토큰 검증
            Claims claims = jwtService.validateToken(jwt);
            return claims.get("email", String.class);
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }
}
