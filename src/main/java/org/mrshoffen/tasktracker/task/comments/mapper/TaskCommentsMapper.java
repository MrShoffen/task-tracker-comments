package org.mrshoffen.tasktracker.task.comments.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentResponseDto;
import org.mrshoffen.tasktracker.task.comments.model.dto.CommentCreateDto;
import org.mrshoffen.tasktracker.task.comments.model.entity.TaskComment;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskCommentsMapper {

    TaskCommentResponseDto toDto(TaskComment taskComment);

    TaskComment toEntity(CommentCreateDto dto, UUID userId, UUID workspaceId, UUID deskId, UUID taskId);
}
