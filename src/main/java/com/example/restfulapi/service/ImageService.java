package com.example.restfulapi.service;

import com.example.restfulapi.dto.response.ImageResponse;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageService {

    private String uploadFile(InputStream inputStream, String fileName, String contentType) throws IOException {
        String[] allowedContentTypes = {"image/jpeg", "image/png"};

        if (!Arrays.asList(allowedContentTypes).contains(contentType)) {
            throw new IllegalArgumentException("File type not supported. Only JPEG and PNG images are allowed.");
        }
        BlobId blobId = BlobId.of("bay-coffee-shop.appspot.com", fileName); // Replace with your bucket name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();

        InputStream credentialsStream = ImageService.class.getClassLoader().getResourceAsStream("firebase.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(credentialsStream);

        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, inputStream);

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/bay-coffee-shop.appspot.com/o/%s?alt=media"; //bucket-name is bay-coffee-shop.appspot.com
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public ImageResponse upload(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();                        // to get original file name
        fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
        InputStream inputStream = multipartFile.getInputStream();                  // get input stream from multipart file
        String contentType = multipartFile.getContentType();                          // get content type of the file
        String URL = this.uploadFile(inputStream, fileName, contentType);             // to get uploaded file link
        ImageResponse response = new ImageResponse();
        response.setImageUrl(URL);
        return response;
    }
}
