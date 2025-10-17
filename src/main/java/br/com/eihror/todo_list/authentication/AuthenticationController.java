package br.com.eihror.todo_list.authentication;

import br.com.eihror.todo_list.authentication.dto.CreateUserRequestDto;
import br.com.eihror.todo_list.authentication.dto.SignInRequestDto;
import br.com.eihror.todo_list.authentication.dto.TokenResponseDto;
import br.com.eihror.todo_list.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Autenticação", description = "Endpoints de Autenticação")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Acessar a aplicaçào")
    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponseDto> signIn(@Valid @RequestBody SignInRequestDto dto) {
        TokenResponseDto responseDto = userService.signIn(dto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Criação conta de usuário")
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody CreateUserRequestDto dto) {
        userService.create(dto);
        return ResponseEntity.noContent().build();
    }
}
