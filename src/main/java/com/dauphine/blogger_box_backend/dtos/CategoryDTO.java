package com.dauphine.blogger_box_backend.dtos;

import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String name
) {}
