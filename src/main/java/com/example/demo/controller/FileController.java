package com.example.demo.controller;

import com.example.demo.dto.Response;
import com.example.demo.dto.file.RemoveFileDto;
import com.example.demo.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileUploadService fileUploadService;

    @PostMapping("/")
    public ResponseEntity<Response> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folderName) {
        Response response = fileUploadService.uploadFile(file, folderName);
        return new ResponseEntity<>(response, response.getStatusCode());
    }

    @DeleteMapping("/")
    public ResponseEntity<Response> deleteFile(@RequestBody RemoveFileDto removeFileDto) {
        log.info("delete file {}", removeFileDto.getPublicId());
        Response response = fileUploadService.removeFile(removeFileDto);
        return new ResponseEntity<>(response, response.getStatusCode());
    }
}
