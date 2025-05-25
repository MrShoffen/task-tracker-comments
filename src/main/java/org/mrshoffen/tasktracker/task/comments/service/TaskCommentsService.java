package org.mrshoffen.tasktracker.task.comments.service;

import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentResponseDto;
import org.mrshoffen.tasktracker.commons.web.exception.EntityNotFoundException;
import org.mrshoffen.tasktracker.task.comments.repository.TaskCommentsRepository;
import org.mrshoffen.tasktracker.task.comments.mapper.TaskCommentsMapper;
import org.mrshoffen.tasktracker.task.comments.model.dto.CommentCreateDto;
import org.mrshoffen.tasktracker.task.comments.model.entity.TaskComment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskCommentsService {

    private final TaskCommentsMapper taskCommentsMapper;

    private final TaskCommentsRepository taskCommentsRepository;

    public Mono<TaskCommentResponseDto> createComment(CommentCreateDto createDto, UUID userId, UUID workspaceId,
                                                      UUID deskId, UUID taskId) {
        TaskComment taskComment = taskCommentsMapper
                .toEntity(createDto, userId, workspaceId, deskId, taskId);

        return taskCommentsRepository
                .save(taskComment)
                .map(taskCommentsMapper::toDto);
    }

    public Flux<TaskCommentResponseDto> getAllComments(UUID workspaceId, UUID taskId) {
        return taskCommentsRepository
                .findAllByWorkspaceIdAndTaskId(workspaceId, taskId)
                .map(taskCommentsMapper::toDto);
    }

    public Mono<Void> deleteAllCommentsInWorkspace(UUID workspaceId) {
        return taskCommentsRepository
                .deleteAllByWorkspaceId(workspaceId);
    }

    public Mono<Void> deleteAllCommentsInTask(UUID workspaceId, UUID taskId) {
        return taskCommentsRepository
                .deleteAllByWorkspaceIdAndTaskId(workspaceId,  taskId);
    }

    public Mono<Void> deleteCommentById(UUID workspaceId, UUID commentId) {
        return taskCommentsRepository
                .findByWorkspaceIdAndId(workspaceId, commentId)
                .switchIfEmpty(
                        Mono.error(new EntityNotFoundException(
                                "Комментарий с id %s не найдена в данном пространстве"
                                        .formatted(commentId.toString())
                        ))
                )
                .flatMap(taskCommentsRepository::delete);
    }
}
