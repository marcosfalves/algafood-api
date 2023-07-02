package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.storage.S3FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
public class StorageConfig {

    private static final String URL_S3_LOCALSTACK = "http://10.0.2.20:4566";

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public S3Client amazonS3() {
        return S3Client.builder()
                .credentialsProvider(getAWSCredentials())
                .region(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "localstack")
    public S3Client amazonS3LocalStack() {
        return S3Client.builder()
                .credentialsProvider(getAWSCredentials())
                .region(storageProperties.getS3().getRegiao())
                .endpointOverride(URI.create(URL_S3_LOCALSTACK))
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        return switch (storageProperties.getTipo()) {
            case S3, LOCALSTACK -> new S3FotoStorageService();
            case LOCAL -> new LocalFotoStorageService();
        };
    }

    private StaticCredentialsProvider getAWSCredentials() {
        var credentials = AwsBasicCredentials.create(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return StaticCredentialsProvider.create(credentials);
    }
}
