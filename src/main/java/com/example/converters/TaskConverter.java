package com.example.converters;

import com.example.dto.TaskDTO;
import com.example.models.Task;
import com.example.models.TaskStatus;

public class TaskConverter {
    public static TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setUserId(task.getUserId());
        dto.setStatus(task.getStatus());
        return dto;
    }

    public static Task convertToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setUserId(dto.getUserId());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.NEW);
        return task;
    }
}
