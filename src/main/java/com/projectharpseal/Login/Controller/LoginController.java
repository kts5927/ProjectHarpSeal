package com.projectharpseal.Login.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.projectharpseal.Login.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login/oauth2", produces = "application/json")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/code/{registrationId}")
    public ResponseEntity<JsonNode> socialLogin(@RequestParam("code") String code, @PathVariable String registrationId) {
        JsonNode userResource = loginService.socialLogin(code, registrationId);
        return ResponseEntity.ok(userResource);
    }
}