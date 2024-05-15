package com.example.restfulapi.controller;

import com.example.restfulapi.dto.response.ImageResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/image/upload")
    public ResourceResponse<ImageResponse>  upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        ImageResponse url = imageService.upload(multipartFile);
        return ResourceResponse.<ImageResponse>builder().message("upload image").status(true).data(url).build();
    }
}
