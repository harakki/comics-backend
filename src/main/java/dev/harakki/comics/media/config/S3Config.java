package dev.harakki.comics.media.config;

import dev.harakki.comics.shared.config.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Configuration
class S3Config {

    Logger logger = Logger.getLogger(getClass().getName());

    private final S3Properties s3Properties;

    @Bean
    public S3Client s3Client() {
        var credentials = AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return S3Client.builder()
                .region(Region.of(s3Properties.getRegion()))
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .forcePathStyle(true)
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        var credentials = AwsBasicCredentials.create(s3Properties.getAccessKey(), s3Properties.getSecretKey());

        return S3Presigner.builder()
                .region(Region.of(s3Properties.getRegion()))
                .endpointOverride(URI.create(s3Properties.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    @Bean
    CommandLineRunner initBucket(S3Client s3Client) {
        return _ -> {
            try {
                s3Client.headBucket(b -> b.bucket(s3Properties.getBucket()));
            } catch (NoSuchBucketException _) {
                s3Client.createBucket(b -> b.bucket(s3Properties.getBucket()));
                logger.info("Bucket '" + s3Properties.getBucket() + "' created.");
            }
        };
    }

}
