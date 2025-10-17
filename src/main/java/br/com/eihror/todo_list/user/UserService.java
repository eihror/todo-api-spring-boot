package br.com.eihror.todo_list.user;

import br.com.eihror.todo_list.authentication.dto.CreateUserRequestDto;
import br.com.eihror.todo_list.authentication.dto.SignInRequestDto;
import br.com.eihror.todo_list.authentication.dto.TokenResponseDto;
import br.com.eihror.todo_list.core.security.JpaUserDetailsService;
import br.com.eihror.todo_list.core.security.JwtService;
import br.com.eihror.todo_list.user.entity.UserEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtService jwtService;

    UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JpaUserDetailsService jpaUserDetailsService,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.jwtService = jwtService;
    }

    public TokenResponseDto signIn(SignInRequestDto dto) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        UserEntity userDetails = jpaUserDetailsService.loadUserByUsername(dto.email());
        Map<String, ?> claims = Map.of("id", userDetails.getId());
        String token = jwtService.generateToken(userDetails, claims);

        Optional<UserEntity> optionalUserEntity = userRepository.findByEmail(dto.email());

        if (optionalUserEntity.isEmpty()) {
            throw new AccessDeniedException("User not found");
        }

        UserEntity user = optionalUserEntity.get();
        user.setToken(token);
        userRepository.save(user);

        return new TokenResponseDto(token);
    }

    public void create(CreateUserRequestDto dto) {
        String cryptedPassword = passwordEncoder.encode(dto.password());

        UserEntity user = UserEntity.builder()
                .name(dto.name())
                .email(dto.email())
                .password(cryptedPassword)
                .build();

        userRepository.save(user);
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
