package com.projectharpseal.Login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectharpseal.Login.Service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value = "/login", produces = "application/json")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/oauth2/code/{registrationId}")
    public void socialLogin(@RequestParam("code") String code,
                            @PathVariable String registrationId,
                            HttpServletResponse response) throws IOException {

        JsonNode userResource = loginService.socialLogin(code, registrationId);

        String jwt = userResource.get("jwt").asText();
        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        response.addCookie(jwtCookie);

        response.sendRedirect("http://localhost:3000/");

    }
}
