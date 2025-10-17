package br.com.eihror.todo_list.user;

import br.com.eihror.todo_list.user.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Usuário", description = "Endpoints de Usuário")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Listar os dados do usuário")
    @GetMapping("/me")
    public ResponseEntity<UserEntity> me(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(userService.findById(user.getId()));
    }
}
