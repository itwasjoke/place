package com.itwasjoke.place.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Создание пользователя")
public record UserRequestDTO(
        @NotNull
        @Size(min = 1, max = 50)
        @Schema(description = "Имя", example = "Андрей")
        String name,
        @NotNull
        @Schema(description = "Логин", example = "itwasjoke")
        String login,
        @NotNull
        @Size(min = 5, max = 50)
        @Schema(description = "Пароль", example = "abc123")
        String password,
        @Schema(description = "Код для входа", example = "WF3FE2")
        String invitationCode
        ) {
}
