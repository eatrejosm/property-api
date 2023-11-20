package mch.asset.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import mch.asset.model.Role;
import mch.asset.security.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public Role getUserPermissions(String token){
        String realTokenValue = token.split(" ")[1];
        var claims = jwtTokenProvider.getClaimsFromToken(realTokenValue).get("additionalInformation");
        return objectMapper.convertValue(claims, Role.class);
    }
}
