package com.example.controllers;

import com.example.models.Task;
import com.example.services.TaskService;
import com.example.dto.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import com.example.converters.TaskConverter;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public TaskDTO createTask(@RequestBody TaskDTO taskDTO) {
        Task task = TaskConverter.convertToEntity(taskDTO);
        return TaskConverter.convertToDTO(taskService.createTask(task));
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return TaskConverter.convertToDTO(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task task = TaskConverter.convertToEntity(taskDTO);
        task.setId(id);
        return TaskConverter.convertToDTO(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks()
                .stream()
                .map(TaskConverter::convertToDTO)
                .collect(Collectors.toList());
    }
}
