package org.mrshoffen.tasktracker.task.comments.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.shaded.io.opentelemetry.proto.trace.v1.ResourceSpans;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentResponseDto;
import org.mrshoffen.tasktracker.commons.web.permissions.Permission;
import org.mrshoffen.tasktracker.task.comments.model.dto.CommentCreateDto;
import org.mrshoffen.tasktracker.task.comments.service.PermissionsService;
import org.mrshoffen.tasktracker.task.comments.service.TaskCommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mrshoffen.tasktracker.commons.web.authentication.AuthenticationAttributes.AUTHORIZED_USER_HEADER_NAME;
import static org.mrshoffen.tasktracker.commons.web.permissions.Permission.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}/desks/{deskId}/tasks/{taskId}/comments")
public class ExternalTaskCommentsController {

    private final PermissionsService permissionsService;

    private final TaskCommentsService taskCommentsService;

    @PostMapping
    Mono<ResponseEntity<TaskCommentResponseDto>> createComment(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                               @Valid @RequestBody Mono<CommentCreateDto> commentCreateDto,
                                                               @PathVariable("workspaceId") UUID workspaceId,
                                                               @PathVariable("deskId") UUID deskId,
                                                               @PathVariable("taskId") UUID taskId) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, CREATE_READ_COMMENTS)
                .then(commentCreateDto
                        .flatMap(dto ->
                                taskCommentsService
                                        .createComment(dto, userId, workspaceId,
                                                deskId, taskId)
                        )
                )
                .map(createdComment ->
                        ResponseEntity.status(HttpStatus.CREATED)
                                .body(createdComment)
                );
    }

    @GetMapping
    Flux<TaskCommentResponseDto> getAllTaskComments(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                                    @PathVariable("workspaceId") UUID workspaceId,
                                                    @PathVariable("deskId") UUID deskId,
                                                    @PathVariable("taskId") UUID taskId) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, CREATE_READ_COMMENTS)
                .thenMany(
                        taskCommentsService.getAllComments(workspaceId, taskId)
                );
    }

    @DeleteMapping("/{commentId}")
    Mono<ResponseEntity<Void>> deleteComment(@RequestHeader(AUTHORIZED_USER_HEADER_NAME) UUID userId,
                                             @PathVariable("workspaceId") UUID workspaceId,
                                             @PathVariable("deskId") UUID deskId,
                                             @PathVariable("taskId") UUID taskId,
                                             @PathVariable("commentId") UUID commentId) {
        return permissionsService
                .verifyUserPermission(userId, workspaceId, DELETE_COMMENTS)
                .then(
                        taskCommentsService
                                .deleteCommentById(workspaceId,  commentId)
                )
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}