package br.com.eihror.todo_list;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.flyway.enabled=false",
        "spring.security.enabled=false"
})
class TodoListApiApplicationTests {

}
