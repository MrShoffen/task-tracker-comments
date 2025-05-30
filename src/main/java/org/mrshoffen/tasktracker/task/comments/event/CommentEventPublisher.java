package org.mrshoffen.tasktracker.task.comments.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.comment.CommentCreatedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.comment.CommentDeletedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.sticker.StickerCreatedEvent;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentResponseDto;
import org.mrshoffen.tasktracker.task.comments.model.dto.CommentCreateDto;
import org.mrshoffen.tasktracker.task.comments.model.entity.TaskComment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentEventPublisher {

    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    public void publishCommentCreatedEvent(TaskCommentResponseDto comment) {
        CommentCreatedEvent event = new CommentCreatedEvent(comment);
        log.info("Event published to kafka topic '{}' - {}", CommentCreatedEvent.TOPIC, event);
        kafkaTemplate.send(CommentCreatedEvent.TOPIC, comment.getId(), event);
    }

    public void publishCommentDeletedEvent(TaskComment comment, UUID deletedBy) {
        CommentDeletedEvent event = CommentDeletedEvent.builder()
                .commentId(comment.getId())
                .deletedAt(Instant.now())
                .taskId(comment.getTaskId())
                .workspaceId(comment.getWorkspaceId())
                .userId(deletedBy)
                .build();
        log.info("Event published to kafka topic '{}' - {}", CommentDeletedEvent.TOPIC, event);
        kafkaTemplate.send(CommentDeletedEvent.TOPIC, comment.getId(), event);
    }
}
