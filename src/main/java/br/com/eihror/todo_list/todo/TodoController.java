package br.com.eihror.todo_list.todo;

import br.com.eihror.todo_list.todo.dto.CreateTodoDto;
import br.com.eihror.todo_list.todo.dto.UpdateTodoDto;
import br.com.eihror.todo_list.todo.entity.TodoEntity;
import br.com.eihror.todo_list.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Tarefas", description = "Endpoints de tarefas")
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @Operation(summary = "Lista todos as tarefas")
    @GetMapping
    public ResponseEntity<List<TodoEntity>> findAll(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(todoService.findAll(user));
    }

    @Operation(summary = "Visualizar os detalhes de uma tarefa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<TodoEntity> findById(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(todoService.findById(user, id));
    }

    @Operation(summary = "Criar uma Tarefa")
    @PostMapping
    public ResponseEntity<TodoEntity> create(
            @AuthenticationPrincipal UserEntity user,
            @Valid @RequestBody CreateTodoDto dto
    ) {
        TodoEntity todoCreated = todoService.create(user, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(todoCreated);
    }

    @Operation(summary = "Editar uma Tarefa")
    @PatchMapping("/{id}")
    public ResponseEntity<TodoEntity> update(
            @AuthenticationPrincipal UserEntity user,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTodoDto dto
    ) {
        TodoEntity todoUpdated = todoService.update(user, id, dto);
        return ResponseEntity.ok(todoUpdated);
    }

    @Operation(summary = "Deletar uma tarefa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
