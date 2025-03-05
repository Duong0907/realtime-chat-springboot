package com.example.demo.dto.conversation;

import com.example.demo.entity.Image;
import lombok.Getter;
import java.time.Instant;

@Getter
public class ImageDto {
    private Long id;
    private String publicId;
    private String url;
    private Instant createdAt;
    private Instant updatedAt;

    public ImageDto(Image image) {
        if (image == null) return;

        this.id = image.getId();
        this.publicId = image.getPublicId();
        this.url = image.getUrl();
        this.createdAt = image.getCreatedAt();
        this.updatedAt = image.getUpdatedAt();
    }
}
