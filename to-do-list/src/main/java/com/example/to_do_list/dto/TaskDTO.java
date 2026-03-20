package com.example.to_do_list.dto;

import com.example.to_do_list.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    @NotBlank(message = "Le titre ne peut pas être vide")
    @Size(min = 3, max = 50, message = "Le titre doit faire entre 3 et 50 caractères")
    private String titre;

    private String description;

    private TaskStatus status;
}
