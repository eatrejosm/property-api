package mch.asset.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "demo.app")
public class DemoAppProperties {
    /**
     * This is JWT Cookie
     */
    private String jwtCookieName;
    private String jwtSecret;
    /**
     * JWT token expiration in milliseconds
     */
    private long jwtExpiration;
    private boolean securityEnableCsrf;
}




