package org.mrshoffen.tasktracker.task.comments.repository;

import org.mrshoffen.tasktracker.task.comments.model.entity.TaskComment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TaskCommentsRepository extends ReactiveCrudRepository<TaskComment, UUID> {

    Mono<Void> deleteAllByWorkspaceId(UUID workspaceId);

    Mono<Void> deleteAllByWorkspaceIdAndDeskId(UUID workspaceId, UUID deskId);

    Flux<TaskComment> findAllByWorkspaceIdAndTaskId(UUID workspaceId, UUID taskId);

    Mono<TaskComment> findByWorkspaceIdAndId(UUID workspaceId, UUID commentId);

    Mono<Void> deleteAllByWorkspaceIdAndTaskId(UUID workspaceId, UUID taskId);
}
