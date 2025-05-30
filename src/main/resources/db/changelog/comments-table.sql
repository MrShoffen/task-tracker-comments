--liquibase formatted sql


--changeset mrshoffen:2
CREATE TABLE IF NOT EXISTS task_comments
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message      VARCHAR(1024) NOT NULL,
    created_at   TIMESTAMP     NOT NULL,
    user_id      UUID          NOT NULL,
    workspace_id UUID          NOT NULL,
    task_id      UUID          NOT NULL
);

CREATE INDEX IF NOT EXISTS comments_workspace_id_id_idx ON task_comments (task_id, workspace_id);

CREATE INDEX IF NOT EXISTS comments_workspace_id_idx ON task_comments (workspace_id);