package com.projectharpseal.Login.Service;

import com.projectharpseal.Login.Entity.Client;
import com.projectharpseal.Login.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    private final UserRepository userRepository;

    // 생성자 주입
    public SignupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signUp(Long id, String email, String password) {
        // 이메일로 기존 사용자 검색
        Optional<Client> existingUser = userRepository.findByEmail(email);

        // 이미 사용자가 존재하면 예외 발생
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 새로운 사용자 생성
        Client newUser = new Client(id, email, password);  // 비밀번호는 해싱해서 저장하는 것이 권장됨
        userRepository.save(newUser);  // 새로운 사용자 저장

        return "success";
    }
}
