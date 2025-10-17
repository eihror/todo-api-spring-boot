package br.com.eihror.todo_list.authentication.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDto(
        @NotBlank String email,
        @NotBlank String password
) { }
