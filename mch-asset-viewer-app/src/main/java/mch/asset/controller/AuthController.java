package mch.asset.controller;

import java.util.*;

import mch.asset.entity.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mch.asset.payload.request.LoginRequest;
import mch.asset.repository.UserRepository;
import mch.asset.security.jwt.JwtTokenProvider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    ReactiveAuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
  
    @PostMapping("/signin")
    public Mono<ResponseEntity> signin(
            @Valid @RequestBody Mono<LoginRequest> authRequest) {

        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.getUsername(), login.getPassword()))
                        .map(this.tokenProvider::createToken))
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();

                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    var tokenBody = Map.of("access_token", jwt);
                    return new ResponseEntity<>(tokenBody, httpHeaders, HttpStatus.OK);
                });

    }

}