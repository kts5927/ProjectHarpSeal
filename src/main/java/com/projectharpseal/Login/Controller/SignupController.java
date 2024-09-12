package com.projectharpseal.Login.Controller;


import com.projectharpseal.Login.Service.SignupService;
import io.jsonwebtoken.io.IOException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/signup", produces = "application/json")

public class SignupController {

    private final SignupService signupService;

    public SignupController(SignupService signupService) {
        this.signupService = signupService;
    }

    @GetMapping("/request")
    public String signuprequest(
                              @RequestParam("email") String email,
                              @RequestParam("password") String password){

        return signupService.signUp(email, password);


    }




}
