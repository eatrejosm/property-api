package mch.asset.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtResponse(String accessToken,  String username, String email) {
        this.token = accessToken;
        this.username = username;
        this.email = email;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

}