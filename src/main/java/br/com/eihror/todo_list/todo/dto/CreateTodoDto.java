package br.com.eihror.todo_list.todo.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoDto(
        @NotBlank String title,
        String description
) { }
