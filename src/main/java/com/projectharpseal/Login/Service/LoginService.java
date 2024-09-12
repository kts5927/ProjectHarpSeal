package com.projectharpseal.Login.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.projectharpseal.Login.Entity.Client;
import com.projectharpseal.Login.Repository.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class LoginService {

    private final Environment env;
    private final RestTemplate restTemplate = new RestTemplate();
    private final UserRepository userRepository;
    private final JWT jwtService; // JWT 서비스 주입


    public LoginService(Environment env, UserRepository userRepository, JWT jwtService) {
        this.env = env;
        this.userRepository = userRepository;
        this.jwtService = jwtService; // JWT 서비스 초기화
    }

    public JsonNode socialLogin(String code, String registrationId) {
        String accessToken = getAccessToken(code, registrationId);
        JsonNode userResourceNode = getUserResource(accessToken);
        System.out.println("userResourceNode = " + userResourceNode);
        System.out.println(accessToken);

        Optional<Client> client = userRepository.findByEmail(
                userResourceNode.get("email").asText()
        );

        // 데이터를 추가하기 위해 JsonNode -> ObjectNode로 캐스팅
        // if문 안쪽과 바깥쪽에 각각 return 하기 때문에 위로 올림
        ObjectNode responseNode = (ObjectNode) userResourceNode;

        //DB에 회원가입 기록이 있는지 확인하기
        String jwtToken;

        if (client.isPresent()) {
            jwtToken = jwtService.generateToken(
                    userResourceNode.get("email").asText(),
                    "True"  // 회원가입이 되어있으면 True
            );
        } else {
            jwtToken = jwtService.generateToken(
                    userResourceNode.get("email").asText(),
                    "False"  // 회원가입이 되어있지 않으면 False
            );
        }

        System.out.println("JWT Token: " + jwtToken);
        responseNode.put("jwt", jwtToken);  // JWT 토큰을 응답에 추가
        return userResourceNode;
    }

    private String getAccessToken(String authorizationCode, String registrationId) {
        String clientId = env.getProperty("Google_Oauth_ID");
        String clientSecret = env.getProperty("Google_Oauth_Secret");
        String redirectUri = env.getProperty("Google_Oauth_RedirectUri");
        String tokenUri = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(tokenUri, HttpMethod.POST, request, JsonNode.class);
        JsonNode accessTokenNode = response.getBody();
        return accessTokenNode.get("access_token").asText();
    }

    private JsonNode getUserResource(String accessToken) {
        String resourceUri = "https://www.googleapis.com/oauth2/v2/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(resourceUri, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
}
