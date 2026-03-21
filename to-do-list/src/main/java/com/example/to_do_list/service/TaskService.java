package com.example.to_do_list.service;

import com.example.to_do_list.dto.TaskDTO;
import com.example.to_do_list.dto.TaskResponseDTO;
import com.example.to_do_list.entity.Task;
import com.example.to_do_list.entity.TaskStatus;
import com.example.to_do_list.entity.User;
import com.example.to_do_list.mapper.TaskMapper;
import com.example.to_do_list.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    private User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public TaskResponseDTO createTask(TaskDTO dto) {
        User currentUser = getAuthenticatedUser();

        Task task = Task.builder()
                .title(dto.getTitre())
                .description(dto.getDescription())
                .status(dto.getStatus() != null ? dto.getStatus() : TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .user(currentUser)
                .build();

        return taskMapper.toDto(taskRepository.save(task));
    }

    public Page<TaskResponseDTO> findAll(Pageable pageable) {
        User currentUser = getAuthenticatedUser();
        Page<Task> taskPage = taskRepository.findByUserId(currentUser.getId(), pageable);

        return taskPage.map(taskMapper::toDto);
    }

    public Task findById(Long id) {
        User currentUser = getAuthenticatedUser();
        return taskRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée ou accès non autorisé"));
    }

    public TaskResponseDTO updateTask(Long id, TaskDTO dto) {
        Task task = findById(id);
        task.setTitle(dto.getTitre());
        task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) {
            task.setStatus(dto.getStatus());
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    public void deleteById(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    public TaskResponseDTO markAsCompleted(Long id) {
        Task task = findById(id);
        task.setStatus(TaskStatus.DONE);
        return taskMapper.toDto(taskRepository.save(task));
    }
}