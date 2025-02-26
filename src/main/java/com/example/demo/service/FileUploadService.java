package com.example.demo.service;

import com.example.demo.dto.Response;
import com.example.demo.dto.file.RemoveFileDto;
import com.example.demo.exception.CustomException;
import com.example.demo.image_upload.FileUploadStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final FileUploadStrategy fileUploadStrategy;

    public Response uploadFile(MultipartFile file, String folderName) throws CustomException {
        Object result = fileUploadStrategy.uploadFile(file, folderName);

        if (result == null) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
        }

        return Response.builder()
                .statusCode(HttpStatus.OK)
                .message("File uploaded successfully")
                .data(result)
                .build();
    }

    public Response removeFile(RemoveFileDto removeFileDto) throws CustomException {
        fileUploadStrategy.removeFile(removeFileDto.getPublicId());

        return Response.builder()
                .statusCode(HttpStatus.OK)
                .message("File removed successfully")
                .build();
    }
}
