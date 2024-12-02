package com.itwasjoke.place.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Пользователь")
public record CoupleResponseDTO(
        @Schema(description = "Имя первого партнера", example = "Андрей")
        String name1,
        @Schema(description = "Имя второго партнера", example = "Алиса")
        String name2,
        @Schema(description = "Дата отношений", example = "14.10.2024")
        Date date
) {
}
