package com.example.controllers;

import com.example.models.Task;
import com.example.services.TaskService;
import com.example.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setUserId(task.getUserId());
        return dto;
    }

    private Task convertToEntity(TaskDTO dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setUserId(dto.getUserId());
        return task;
    }

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        return convertToDTO(taskService.createTask(task));
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return convertToDTO(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        task.setId(id);
        return convertToDTO(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
