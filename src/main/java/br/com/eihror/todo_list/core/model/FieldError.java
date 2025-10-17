package br.com.eihror.todo_list.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldError {
    private String field;
    private String message = "";
    private int code;
}
