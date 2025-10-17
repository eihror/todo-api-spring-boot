package br.com.eihror.todo_list.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTodoDto(
        @NotBlank
        String title,
        String description,
        boolean completed
) { }
