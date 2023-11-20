package mch.asset.security.jwt;

import mch.asset.config.DemoAppProperties;
import mch.asset.model.Role;
import mch.asset.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import static java.util.stream.Collectors.joining;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";

    private String jwtSecret;
  
    private Long jwtExpirationMs;

    private final UserRepository userRepository;

    @Autowired
    public JwtTokenProvider(DemoAppProperties appProperties, UserRepository userRepository) {
        this.jwtExpirationMs = appProperties.getJwtExpiration();
        this.jwtSecret = appProperties.getJwtSecret();
        this.userRepository = userRepository;
    }
  
    public String createToken(Authentication authentication) {

        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(joining(",")));
        }
      
        /* Set securityGroupId and OrganizationId */
        userRepository.findByUsername(authentication.getName()).ifPresent(
                user -> {
                    var userRole = Role.builder()
                            .groupId(user.getGroupId())
                            .propertyManagerId(user.getOrganizationId())
                            .build();
                    claims.put("additionalInformation", userRole);
                }
        );
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();

    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

        Collection<? extends GrantedAuthority> authorities = authoritiesClaim == null
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Map<String, Object> getClaimsFromToken(String token) {
        log.info("Token received: {}", token);
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            // parseClaimsJws will check expiration date. No need do here.
            log.info("expiration date: {}", claims.getBody().getExpiration());
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token: {}", e.getMessage());
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

}
