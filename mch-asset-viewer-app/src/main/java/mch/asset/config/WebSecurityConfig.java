package mch.asset.config;

import mch.asset.repository.UserRepository;
import mch.asset.security.jwt.JwtTokenAuthenticationFilter;
import mch.asset.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mch.common.api.reactor.ReactiveWrapperService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class WebSecurityConfig {
    private final ReactiveWrapperService reactiveWrapperService;
    private final UserRepository users;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return getMd5(charSequence.toString());
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return getMd5(charSequence.toString()).equals(s);
            }
        };
    }
    public static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing SHA
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(input.getBytes(), 0, input.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            log.debug("Exception thrown  for incorrect algorithm: " + e);
            return null;
        }
    }

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http,
                                                JwtTokenProvider tokenProvider,
                                                ReactiveAuthenticationManager reactiveAuthenticationManager) {
        final String PATH_POSTS = "/posts/**";

        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange(it -> it
                        .pathMatchers("/signin").permitAll()
                        .pathMatchers(HttpMethod.DELETE, PATH_POSTS).hasRole("ADMIN")
                        .pathMatchers(PATH_POSTS).authenticated()
                        .pathMatchers("/me").authenticated()
                        .pathMatchers("/users/{user}/**").access(this::currentUserMatchesPath)
                        .anyExchange().permitAll())
                .addFilterAt(new JwtTokenAuthenticationFilter(tokenProvider),
                        SecurityWebFiltersOrder.HTTP_BASIC)
                .build();

    }

    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication,
                                                               AuthorizationContext context) {

        return authentication
                .map(a -> context.getVariables().get("user").equals(a.getName()))
                .map(AuthorizationDecision::new);

    }

    private String[] role = {"ROLE_USER"};

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> reactiveWrapperService.asyncMono(() ->
                users.findByUsername(username)
                        .map(u -> User
                                .withUsername(u.getUsername())
                                .password(u.getPassword())
                                .authorities(role)
                                .accountExpired(u.getActive() != 1)
                                .credentialsExpired(u.getActive() != 1)
                                .disabled(u.getActive() != 1)
                                .accountLocked(u.getActive() != 1)
                                .build())
                        .orElse(null));
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

}
