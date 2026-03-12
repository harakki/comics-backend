package dev.harakki.comics.shared.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String region;
    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucket;

}
