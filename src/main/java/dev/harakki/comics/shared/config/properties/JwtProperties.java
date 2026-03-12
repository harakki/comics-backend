package dev.harakki.comics.shared.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {

    private Duration connectTimeout;
    private Duration readTimeout;

}
