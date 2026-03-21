package com.example.to_do_list.service;

import com.example.to_do_list.dto.TaskDTO;
import com.example.to_do_list.dto.TaskResponseDTO;
import com.example.to_do_list.entity.Task;
import com.example.to_do_list.entity.User;
import com.example.to_do_list.mapper.TaskMapper;
import com.example.to_do_list.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L)
                .username("Armel")
                .build();
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(auth.getPrincipal()).thenReturn(mockUser);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testCreateTask() {
        TaskDTO dto = new TaskDTO();
        dto.setTitre("Nouvelle tâche");

        Task savedTask = Task.builder()
                .id(10L)
                .title("Nouvelle tâche")
                .user(mockUser)
                .build();

        TaskResponseDTO responseDTO = new TaskResponseDTO();
        responseDTO.setId(10L);
        responseDTO.setTitle("Nouvelle tâche");

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);
        when(taskMapper.toDto(any(Task.class))).thenReturn(responseDTO);

        TaskResponseDTO result = taskService.createTask(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(taskRepository).save(argThat(task ->
                task.getUser().getId().equals(1L)
        ));
    }

    @Test
    public void testFindById_Success() {
        Task task = Task.builder().id(1L).title("Test").user(mockUser).build();
        when(taskRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(task));

        Task result = taskService.findById(1L);

        assertEquals("Test", result.getTitle());
    }

    @Test
    public void testFindById_NotFound() {
        when(taskRepository.findByIdAndUserId(99L, 1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.findById(99L));
    }
}