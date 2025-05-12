package com.example.dto;

import com.example.models.TaskStatus;
import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private Long userId;
    private TaskStatus status;
}
