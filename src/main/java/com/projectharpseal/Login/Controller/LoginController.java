package com.projectharpseal.Login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectharpseal.Login.Service.LoginService;
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

        String name = URLEncoder.encode(userResource.get("name").asText(), StandardCharsets.UTF_8);
        String id = URLEncoder.encode(userResource.get("id").asText(), StandardCharsets.UTF_8);
        String email = URLEncoder.encode(userResource.get("email").asText(), StandardCharsets.UTF_8);
        String jwt = URLEncoder.encode(userResource.get("jwt").asText(), StandardCharsets.UTF_8);
        String redirectUrl = "http://localhost:3000/login?name=" + name + "&id=" + id + "&email=" + email+"&jwt="+jwt;

        response.sendRedirect(redirectUrl);
    }
}
