package com.algaworks.algafood.infrastructure.storage;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URL;

public class S3FotoStorageService implements FotoStorageService {

    @Autowired
    private S3Client s3Client;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            GetUrlRequest request = GetUrlRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(caminhoArquivo)
                    .build();

            URL url = s3Client.utilities().getUrl(request);

            return FotoRecuperada.builder()
                    .url(url.toString())
                    .build();
        } catch (Exception e) {
            throw new StorageException("Não foi possível recuperar arquivo na Amazon S3.", e);
        }
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            var putObjectRequest = PutObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(caminhoArquivo)
                    .contentType(novaFoto.getContentType())
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build();

            var requestBody = RequestBody.fromInputStream(
                    novaFoto.getInputStream(),
                    novaFoto.getTamanho()
            );

            s3Client.putObject(putObjectRequest, requestBody);
        } catch (Exception e) {
            throw new StorageException("Não foi possível enviar arquivo para Amazon S3.", e);
        }
    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

            var deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(storageProperties.getS3().getBucket())
                    .key(caminhoArquivo)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
        }
    }
}
