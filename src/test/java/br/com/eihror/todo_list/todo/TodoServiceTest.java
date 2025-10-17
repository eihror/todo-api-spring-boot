package br.com.eihror.todo_list.todo;

import br.com.eihror.todo_list.todo.dto.CreateTodoDto;
import br.com.eihror.todo_list.todo.dto.UpdateTodoDto;
import br.com.eihror.todo_list.todo.entity.TodoEntity;
import br.com.eihror.todo_list.user.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private UserEntity user;
    private TodoEntity expectedResult;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .id(UUID.randomUUID())
                .build();

        expectedResult = TodoEntity.builder()
                .id(UUID.randomUUID())
                .user(user)
                .build();
    }

    @Test
    void testFindTodoList() {
        when(todoRepository.findAllByUserId(Mockito.any(UUID.class))).thenReturn(new ArrayList<>());

        List<TodoEntity> todoList = todoService.findAll(user);

        Assertions.assertTrue(todoList.isEmpty());
    }

    @Test
    void testFindTodoById() {
        final TodoEntity expectedResult = TodoEntity.builder()
                .id(UUID.randomUUID())
                .build();

        when(todoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(expectedResult));

        TodoEntity todo = todoService.findById(user, expectedResult.getId());

        Assertions.assertEquals(expectedResult, todo);
    }

    @Test
    void testCreateTodo() {
        final CreateTodoDto dto = new CreateTodoDto("Title test", "Description test");
        expectedResult.setTitle(dto.title());
        expectedResult.setDescription(dto.description());

        when(todoRepository.save(Mockito.any(TodoEntity.class)))
                .thenAnswer(invocationOnMock -> expectedResult);


        TodoEntity todo = todoService.create(user, dto);

        Assertions.assertEquals(expectedResult.getUser(), todo.getUser());
    }

    @Test
    void testUpdateTodo() {
        final UpdateTodoDto dto = new UpdateTodoDto("New title Test", "New description test", true);
        expectedResult.setTitle(dto.title());
        expectedResult.setDescription(dto.description());
        expectedResult.setCompleted(dto.completed());

        when(todoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(expectedResult));

        when(todoRepository.save(Mockito.any(TodoEntity.class)))
                .thenAnswer(invocationOnMock -> expectedResult);

        TodoEntity todo = todoService.update(user, expectedResult.getId(), dto);

        Assertions.assertEquals(expectedResult.getId(), todo.getId());
    }

    @Test
    void testDeleteTodo() {
        doNothing().when(todoRepository).deleteById(expectedResult.getId());

        todoService.delete(expectedResult.getId());

        verify(todoRepository, times(1)).deleteById(expectedResult.getId());
    }
}