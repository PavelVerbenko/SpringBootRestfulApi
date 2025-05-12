package com.example.kafka;

import com.example.dto.TaskStatusUpdateDTO;
import com.example.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskUpdateConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "task-updates", groupId = "task-group", batch = "true")
    public void listen(List<TaskStatusUpdateDTO> updates, Acknowledgment ack) {
        try {
            for (TaskStatusUpdateDTO update : updates) {
                if (update == null) {
                    log.warn("Received null message in batch");
                    continue;
                }
                processUpdate(update);
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing batch: {}", e.getMessage());
        }
    }

    private void processUpdate(TaskStatusUpdateDTO update) {
        try {
            log.info("Processing update: {}", update);
            String message = String.format(
                    "Task ID: %d status changed from %s to %s (User ID: %d)",
                    update.getTaskId(),
                    update.getOldStatus(),
                    update.getNewStatus(),
                    update.getUserId()
            );

            notificationService.sendEmail("ya.pasha.verbenko@yandex.ru", "Task Update", message);
            notificationService.sendEmail("admin@example.com", "Task Update", message);
        } catch (Exception e) {
            log.error("Failed to process update: {}", update, e);
        }
    }
}