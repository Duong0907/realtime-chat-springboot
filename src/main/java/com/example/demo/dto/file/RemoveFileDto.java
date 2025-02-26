package com.example.demo.dto.file;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RemoveFileDto {
    @JsonProperty(value = "public_id")
    private String publicId;
}
