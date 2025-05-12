package com.example.services;

import com.example.dto.TaskStatusUpdateDTO;
import com.example.models.Task;
import com.example.models.TaskStatus;
import com.example.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final KafkaTemplate<String, TaskStatusUpdateDTO> kafkaTemplate;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = getTaskById(id);
        TaskStatus oldStatus = task.getStatus();

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setUserId(taskDetails.getUserId());
        task.setStatus(taskDetails.getStatus() != null ? taskDetails.getStatus() : TaskStatus.NEW);

        boolean statusChanged = !oldStatus.equals(task.getStatus());
        Task savedTask = taskRepository.save(task);

        if (statusChanged) {
            TaskStatusUpdateDTO updateDTO = new TaskStatusUpdateDTO();
            updateDTO.setTaskId(id);
            updateDTO.setOldStatus(oldStatus.name());
            updateDTO.setNewStatus(task.getStatus().name());
            updateDTO.setUserId(task.getUserId());

            try {
                CompletableFuture<SendResult<String, TaskStatusUpdateDTO>> future =
                        kafkaTemplate.send("task-updates", String.valueOf(id), updateDTO);

                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Status update sent: {}", updateDTO);
                    } else {
                        log.error("Failed to send status update: {}", updateDTO, ex);
                    }
                });
            } catch (Exception e) {
                log.error("Error sending Kafka message", e);
            }
        }

        return savedTask;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}