package com.dauphine.blogger_box_backend.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record PostDTO(
        UUID id,
        String title,
        String content,
        UUID categoryId,
        LocalDateTime createdAt
) {}
