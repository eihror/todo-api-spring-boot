package br.com.eihror.todo_list.core.model;

import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public final class ErrorCodes {
    private static final Map<String, Integer> ERROR_MAP = new HashMap<>();

    static {
        ERROR_MAP.put("NotBlank.createTodoDto.title", 1001);
        ERROR_MAP.put("NotBlank.updateTodoDto.title", 1001);
    }

    public static int resolve(FieldError error) {
        String key = String.format("%s.%s.%s", error.getCode(), error.getObjectName(), error.getField());
        return ERROR_MAP.getOrDefault(key, 9999);
    }
}
