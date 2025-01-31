package com.vtech.ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AwsConfig {

//    @Value("${aws.access-key}")
//    private String accessKey;
//
//    @Value("${aws.secret-key}")
//    private String secretKey;

    @Value("${aws.region}")
    private String region;

    @Bean
    public SecretsManagerClient secretsManagerClient() {
//        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKey, secretKey);
//        return SecretsManagerClient.builder()
//                .region(Region.of(region))
//                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
//                .build();
        
        return SecretsManagerClient.builder()
                .region(Region.of(region)) // specify the region
                .build(); 
    }
}

