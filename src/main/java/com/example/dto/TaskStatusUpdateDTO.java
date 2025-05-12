package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDTO {
    private Long taskId;
    private String oldStatus;
    private String newStatus;
    private Long userId;
}
