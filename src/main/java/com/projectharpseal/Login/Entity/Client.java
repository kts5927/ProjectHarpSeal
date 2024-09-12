package com.projectharpseal.Login.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long key;

    private String username;
    private String password;
    private String email;


    @Builder
    public Client(long key, String username, String password, String email) {
        this.key = key;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
