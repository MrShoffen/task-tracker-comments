package org.mrshoffen.tasktracker.task.comments.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table("task_comments")
public class TaskComment {

    @Id
    @Column("id")
    UUID id;

    @Column("message")
    private String message;

    @Column("created_at")
    private Instant createdAt = Instant.now();

    @Column("user_id")
    private UUID userId;

    @Column("workspace_id")
    private UUID workspaceId;

    @Column("task_id")
    private UUID taskId;

}
