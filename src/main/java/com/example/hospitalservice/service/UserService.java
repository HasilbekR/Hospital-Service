package com.example.hospitalservice.service;

import com.example.hospitalservice.dto.response.SetEmployment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    @Value("${services.set-employment}")
    private String setData;

    public void setEmployment(Principal principal, UUID hospitalId) {

        SetEmployment employment = new SetEmployment(principal.getName(), hospitalId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Authorization", "Bearer " + jwtService.generateAccessTokenForService("USER-SERVICE"));
        HttpEntity<SetEmployment> entity = new HttpEntity<>(employment, httpHeaders);
        restTemplate.exchange(
                URI.create(setData),
                HttpMethod.POST,
                entity,
                String.class);

    }
}

