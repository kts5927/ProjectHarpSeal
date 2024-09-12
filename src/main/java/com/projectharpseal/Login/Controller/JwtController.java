package com.projectharpseal.Login.Controller;


import com.projectharpseal.Login.Service.JWT;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/JWT", produces = "application/json")
public class JwtController {

    private final JWT jwtService;

    public JwtController(JWT jwt) {
        this.jwtService = jwt;
    }

    @PostMapping("/verify")
    public String verify(@RequestParam("jwt") String token) {
        try {
            // JWT 토큰 검증
            Claims claims = jwtService.validateToken(token);
            String tf = claims.get("TF", String.class);

            if(tf.equals("True")){
                return "Login successful";
            }else{
                return "Login failed";
            }
            // 검증 결과 반환
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }

    @PostMapping("/email")
    public String email(@RequestParam("jwt") String jwt) {
        try {
            // JWT 토큰 검증
            Claims claims = jwtService.validateToken(jwt);

            return claims.get("email", String.class);
            // 검증 결과 반환
        } catch (Exception e) {
            return "Invalid token: " + e.getMessage();
        }
    }

}
