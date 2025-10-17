package br.com.eihror.todo_list.todo;

import br.com.eihror.todo_list.todo.entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoRepository extends JpaRepository<TodoEntity, UUID> {
    List<TodoEntity> findAllByUserId(UUID userId);
}
