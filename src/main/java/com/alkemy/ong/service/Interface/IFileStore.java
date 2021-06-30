package com.alkemy.ong.service.Interface;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface IFileStore {

    void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream);

    byte[] download(String path, String key);

    Map<String, String> extractMetadata(MultipartFile file);

    void isAnImage(MultipartFile file);

    void fileIsEmpty(MultipartFile file);

    void deleteFilesFromS3Bucket(String fileUrl);

}
