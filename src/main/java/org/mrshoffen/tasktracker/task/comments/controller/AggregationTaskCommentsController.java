package org.mrshoffen.tasktracker.task.comments.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentResponseDto;
import org.mrshoffen.tasktracker.commons.web.dto.TaskCommentsCountDto;
import org.mrshoffen.tasktracker.task.comments.service.TaskCommentsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aggregate-api/workspaces")
public class AggregationTaskCommentsController {

    private final TaskCommentsService taskCommentsService;

    @GetMapping("/{workspaceId}/comments-count")
    Flux<TaskCommentsCountDto> getCommentsCount(@PathVariable("workspaceId") UUID workspaceId) {
        return taskCommentsService
                .getCommentsCount(workspaceId);
    }


}
