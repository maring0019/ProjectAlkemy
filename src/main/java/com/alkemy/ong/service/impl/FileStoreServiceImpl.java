package com.alkemy.ong.service.impl;

import com.alkemy.ong.service.Interface.IFileStore;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import static org.apache.http.entity.ContentType.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@AllArgsConstructor
public class FileStoreServiceImpl implements IFileStore {

    private final AmazonS3 s3;

    //@Value("${aws.s3.bucket.name}")
    private final String bucketName = "funerariadb-images";


    @Override
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata); //map.forEach((key, value) -> objectMetadata.addUserMetadata(key, value));
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, metadata);
        } catch (AmazonServiceException ex) {
            throw new IllegalStateException("Fallo al almacenar el archivo en S3: ", ex);
        }
    }

    @Override
    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            return IOUtils.toByteArray(object.getObjectContent());
        } catch (AmazonServiceException | IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    //Metadata extraction from the file
    @Override
    public Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    //Validate if the file corresponds to an image
    @Override
    public void isAnImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_PNG.getMimeType(), IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("El archivo debe ser una imagen valida (JPEG, JPG, PNG, GIF).");
        }
    }

    //Validate if the file is empty
    @Override
    public void fileIsEmpty(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalStateException("No se puede subir un archivo vacio.");
        }
    }

    @Override
    public void deleteFilesFromS3Bucket(String fileUrl) {
        try {
            for(S3ObjectSummary file : s3.listObjects(bucketName, fileUrl).getObjectSummaries()) {
                s3.deleteObject(bucketName, file.getKey());
            }
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
