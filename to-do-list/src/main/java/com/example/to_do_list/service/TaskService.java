package com.example.to_do_list.service;

import com.example.to_do_list.dto.TaskDTO;
import com.example.to_do_list.dto.TaskResponseDTO;
import com.example.to_do_list.entity.Task;
import com.example.to_do_list.entity.TaskStatus;
import com.example.to_do_list.mapper.TaskMapper;
import com.example.to_do_list.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task createTask(TaskDTO dto){
        Task task = Task.builder()
                .title(dto.getTitre())
                .description(dto.getDescription())
                .status(dto.getStatus()!=null?dto.getStatus():TaskStatus.TODO)
                .build();
        return taskRepository.save(task);
    }

    public Page<TaskResponseDTO> findAll(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);

        return taskPage.map(taskMapper::toDto);
    }
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(()-> new RuntimeException("Tâche non trouvée avec l'id : " + id));
    }
    public  Task updateTask(Long id,Task taskDetails){
        Task task = findById(id);
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        return taskRepository.save(task);
    }
    public void deleteById(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }
    public  Task markAsCompleted(Long id){
        Task task = findById(id);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
