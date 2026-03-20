package com.example.to_do_list.service;

import com.example.to_do_list.dto.TaskDTO;
import com.example.to_do_list.entity.Task;
import com.example.to_do_list.entity.TaskStatus;
import com.example.to_do_list.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;
    @Test
    public void testFindById() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> taskService.findById(99L));
        assertTrue(exception.getMessage().contains("Tâche non trouvée"));
    }
    @Test
    public void testSave() {
        TaskDTO  taskDTO = new TaskDTO();
        taskDTO.setTitre("Test");
        taskDTO.setDescription("test");
        Task savedTask = Task.builder()
                .id(1L)
                .title("Test")
                .description("test")
                .status(TaskStatus.TODO)
                .build();
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        Task result = taskService.createTask(taskDTO);
        assertNotNull(result);
        assertEquals("Test", result.getTitle());
        assertEquals(TaskStatus.TODO, result.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));

    }
    @Test
    public void testDelete() {
    }
    @Test
    public void testUpdate() {}
    @Test
    public void testFindAll() {}
}
