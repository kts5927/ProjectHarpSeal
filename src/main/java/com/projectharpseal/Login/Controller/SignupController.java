package com.projectharpseal.Login.Controller;

import com.projectharpseal.Login.Service.SignupService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/signup", produces = "application/json")
public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @PostMapping("/request")
    public String signupRequest(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        return signupService.signUp(email, password);
    }
}
