--liquibase formatted sql


--changeset mrshoffen:2
CREATE TABLE IF NOT EXISTS task_comments
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    message      VARCHAR(1024) NOT NULL,
    created_at   TIMESTAMP     NOT NULL,
    user_id      UUID          NOT NULL,
    workspace_id UUID          NOT NULL,
    desk_id      UUID          NOT NULL,
    task_id      UUID          NOT NULL
);