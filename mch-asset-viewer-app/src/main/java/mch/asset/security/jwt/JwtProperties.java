package mch.asset.security.jwt;

import lombok.Data;

import mch.asset.config.DemoAppProperties;


@Data
public class JwtProperties {

    private String secretKey;

    private long validityInMs; 

    public JwtProperties(DemoAppProperties appProperties){
        this.secretKey=appProperties.getJwtSecret();
        this.validityInMs=appProperties.getJwtExpiration();
    }

}
