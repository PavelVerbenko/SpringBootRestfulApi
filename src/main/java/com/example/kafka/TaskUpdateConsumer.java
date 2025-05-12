package com.example.kafka;

import com.example.dto.TaskStatusUpdateDTO;
import com.example.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskUpdateConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "task-updates", groupId = "task-group")
    public void listen(TaskStatusUpdateDTO update, Acknowledgment ack) {
        try {
            log.info("Received task status update: {}", update);
            String message = String.format(
                    "Task ID: %d status changed from %s to %s (User ID: %d)",
                    update.getTaskId(),
                    update.getOldStatus(),
                    update.getNewStatus(),
                    update.getUserId()
            );
            notificationService.sendEmail("ya.pasha.verbenko@yandex.ru", "Task Status Update", message);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing status update: {}", update, e);
        }
    }

    @KafkaListener(topics = "task-updates", groupId = "task-group")
    public void listen(
            @Payload(required = false) TaskStatusUpdateDTO update,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            Acknowledgment ack,
            ConsumerRecordMetadata meta) {

        if (update == null) {
            log.warn("Received null message with key: {}", key);
            return;
        }

        try {
            log.info("Received task status update: {}", update);
            String message = String.format(
                    "Task ID: %d status changed from %s to %s (User ID: %d)",
                    update.getTaskId(),
                    update.getOldStatus(),
                    update.getNewStatus(),
                    update.getUserId()
            );
            notificationService.sendEmail("ya.pasha.verbenko@yandex.ru", "Task Status Update", message);
            notificationService.sendEmail("admin@example.com", "Task Status Update", message);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Error processing status update: {}", update, e);
        }
    }
}