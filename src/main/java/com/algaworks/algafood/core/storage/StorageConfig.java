package com.algaworks.algafood.core.storage;

import com.algaworks.algafood.core.storage.StorageProperties.TipoStorage;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.infrastructure.storage.LocalFotoStorageService;
import com.algaworks.algafood.infrastructure.storage.S3FotoStorageService;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class StorageConfig {

    @Autowired
    private StorageProperties storageProperties;

//    @Profile("production")
//    @Bean
//    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
//    public AmazonS3 amazonS3() {
//        var credentials = new BasicAWSCredentials(
//                storageProperties.getS3().getIdChaveAcesso(),
//                storageProperties.getS3().getChaveAcessoSecreta());
//
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(storageProperties.getS3().getRegiao())
//                .build();
//    }

    @Profile("development")
    @Bean
    @ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
    public AmazonS3 amazonS3() {

        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdChaveAcesso(),
                storageProperties.getS3().getChaveAcessoSecreta());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder
                        .EndpointConfiguration("http://s3.localhost.localstack.cloud:4566", storageProperties.getS3().getRegiao().getName()))
                //.withRegion(storageProperties.getS3().getRegiao())
                .build();
    }

    @Bean
    public FotoStorageService fotoStorageService() {
        if (storageProperties.getTipo() == TipoStorage.S3) {
            return new S3FotoStorageService();
        } else {
            return new LocalFotoStorageService();
        }
    }
}
