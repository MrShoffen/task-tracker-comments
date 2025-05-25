package org.mrshoffen.tasktracker.task.comments.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrshoffen.tasktracker.commons.kafka.event.desk.DeskDeletedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.task.TaskDeletedEvent;
import org.mrshoffen.tasktracker.commons.kafka.event.workspace.WorkspaceDeletedEvent;
import org.mrshoffen.tasktracker.task.comments.service.TaskCommentsService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventListener {

    private final TaskCommentsService commentsService;

    @KafkaListener(topics = WorkspaceDeletedEvent.TOPIC)
    public void handleWorkspaceDeletedEvent(WorkspaceDeletedEvent event) {
        log.info("Received event in topic {} - {}", WorkspaceDeletedEvent.TOPIC, event);
        commentsService
                .deleteAllCommentsInWorkspace(event.getWorkspaceId())
                .block();
    }

    @KafkaListener(topics = TaskDeletedEvent.TOPIC)
    public void handleTaskDeletedEvent(TaskDeletedEvent event) {
        log.info("Received event in topic {} - {}", TaskDeletedEvent.TOPIC, event);
        commentsService
                .deleteAllCommentsInTask(event.getWorkspaceId(), event.getTaskId())
                .block();
    }


}
