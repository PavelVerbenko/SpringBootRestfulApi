package com.example.dto;

import lombok.Data;

@Data
public class TaskStatusUpdateDTO {
    private Long taskId;
    private String oldStatus;
    private String newStatus;
    private Long userId;
}
