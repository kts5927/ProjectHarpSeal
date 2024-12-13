package com.projectharpseal.Login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectharpseal.Login.Service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private final LoginService loginService;
    private final Environment env;

    public LoginController(LoginService loginService, Environment environment ) {
        this.loginService = loginService;
        this.env = environment;
    }

    @GetMapping("/oauth2/code/{registrationId}")
    public void socialLogin(@RequestParam("code") String code,
                            @PathVariable String registrationId,
                            HttpServletResponse response) throws IOException {

        JsonNode userResource = loginService.socialLogin(code, registrationId);

        String jwt = userResource.get("jwt").asText();

        // ResponseCookie 사용
        ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                .domain(env.getProperty("CookieHOST")) // 쿠키 도메인 설정
                .path("/")                            // 모든 경로에 적용
                .secure(true)                         // HTTPS 환경에서만 전송
                .sameSite("None")                     // Cross-Domain 허용
                .maxAge(24 * 60 * 60)                 // 1일
                .build();

        response.addHeader("Set-Cookie", jwtCookie.toString());

        response.sendRedirect(env.getProperty("HOST")); // 클라이언트로 리다이렉트
    }
}
