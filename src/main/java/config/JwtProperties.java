//package config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "app")
//public class JwtProperties {
//    private String jwtSecret;
//    private long jwtExpirationMilliseconds;
//
//    // Getters and Setters
//    public String getJwtSecret() {
//        return jwtSecret;
//    }
//
//    public void setJwtSecret(String jwtSecret) {
//        this.jwtSecret = jwtSecret;
//    }
//
//    public long getJwtExpirationMilliseconds() {
//        return jwtExpirationMilliseconds;
//    }
//
//    public void setJwtExpirationMilliseconds(long jwtExpirationMilliseconds) {
//        this.jwtExpirationMilliseconds = jwtExpirationMilliseconds;
//    }
//}

package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class JwtProperties {
    private String jwtSecret;
    private int jwtExpirationMilliseconds = 86400000; // 24 hours

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getJwtExpirationMilliseconds() {
        return jwtExpirationMilliseconds;
    }

    public void setJwtExpirationMilliseconds(int jwtExpirationMilliseconds) {
        this.jwtExpirationMilliseconds = jwtExpirationMilliseconds;
    }
}