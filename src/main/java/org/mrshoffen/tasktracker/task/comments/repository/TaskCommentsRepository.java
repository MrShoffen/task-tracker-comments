package org.mrshoffen.tasktracker.task.comments.repository;

import jakarta.ws.rs.QueryParam;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentsCountDto;
import org.mrshoffen.tasktracker.task.comments.model.entity.TaskComment;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TaskCommentsRepository extends ReactiveCrudRepository<TaskComment, UUID> {

    Mono<Void> deleteAllByWorkspaceId(UUID workspaceId);

    Flux<TaskComment> findAllByWorkspaceIdAndTaskId(UUID workspaceId, UUID taskId);

    Mono<TaskComment> findByWorkspaceIdAndId(UUID workspaceId, UUID commentId);

    Mono<Void> deleteAllByWorkspaceIdAndTaskId(UUID workspaceId, UUID taskId);

    @Query("SELECT * from task_comments WHERE task_id=:taskId AND workspace_id=:workspaceId ORDER BY created_at DESC LIMIT :limit OFFSET :offset ")
    Flux<TaskComment> findAllByTaskIdWithOffsetAndLimit(@Param("workspaceId") UUID workspaceId, @Param("taskId") UUID taskId, long offset, long limit);

    @Query("""
        SELECT task_id, COUNT(*) as count 
        FROM task_comments 
        WHERE workspace_id = :workspaceId 
        GROUP BY task_id
        """)
    Flux<TaskCommentsCountDto> countCommentsByTaskIdForWorkspace(UUID workspaceId);
}
