package br.com.eihror.todo_list.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenResponseDto(
        @JsonProperty("access_token")
        String accessToken
) { }
