package br.com.eihror.todo_list.todo;

import br.com.eihror.todo_list.todo.dto.CreateTodoDto;
import br.com.eihror.todo_list.todo.dto.UpdateTodoDto;
import br.com.eihror.todo_list.todo.entity.TodoEntity;
import br.com.eihror.todo_list.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoEntity> findAll(UserEntity user) {
        return todoRepository.findAllByUserId(user.getId());
    }

    public TodoEntity findById(UserEntity user, UUID id) {
        return todoRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public TodoEntity create(UserEntity user, CreateTodoDto dto) {
        TodoEntity todo = TodoEntity.builder()
                .title(dto.title())
                .description(dto.description())
                .user(user)
                .completed(false)
                .build();

        return todoRepository.save(todo);
    }

    public TodoEntity update(UserEntity user, UUID id, UpdateTodoDto dto) {
        TodoEntity todoFound = this.findById(user, id);

        todoFound.setTitle(dto.title());
        todoFound.setDescription(dto.description());
        todoFound.setCompleted(dto.completed());

        return todoRepository.save(todoFound);
    }

    public void delete(UUID id) {
        todoRepository.deleteById(id);
    }
}
