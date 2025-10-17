package br.com.eihror.todo_list.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequestDto(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password
) {}
