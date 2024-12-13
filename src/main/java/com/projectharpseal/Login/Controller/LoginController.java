package com.projectharpseal.Login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectharpseal.Login.Service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
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
        Cookie jwtCookie = new Cookie("jwt", jwt);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(24 * 60 * 60);
        jwtCookie.setDomain(env.getProperty("CookieHOST"));
        response.addCookie(jwtCookie);
        response.sendRedirect(env.getProperty("HOST"));

    }
}
