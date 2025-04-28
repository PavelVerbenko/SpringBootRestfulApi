package dto;

import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private Long userId;
}
