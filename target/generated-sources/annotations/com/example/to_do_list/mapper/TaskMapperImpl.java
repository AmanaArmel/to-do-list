package com.example.to_do_list.mapper;

import com.example.to_do_list.dto.TaskResponseDTO;
import com.example.to_do_list.entity.Task;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-21T11:32:29+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskResponseDTO toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponseDTO.TaskResponseDTOBuilder taskResponseDTO = TaskResponseDTO.builder();

        taskResponseDTO.id( task.getId() );
        taskResponseDTO.title( task.getTitle() );
        taskResponseDTO.description( task.getDescription() );
        taskResponseDTO.status( task.getStatus() );
        taskResponseDTO.createdAt( task.getCreatedAt() );

        return taskResponseDTO.build();
    }
}
