package com.example.to_do_list.mapper;

import com.example.to_do_list.dto.TaskResponseDTO;
import com.example.to_do_list.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponseDTO toDto(Task task);
}
