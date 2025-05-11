package org.mrshoffen.tasktracker.task.comments.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentCreateDto(
        @Size(max = 1024, min = 1, message = "Сообщение должно быть от 1 до 1024 символов")
        @NotBlank(message = "Сообщение не может быть пустым")
        String message
) {

}
