package br.com.eihror.todo_list.core.security;

import br.com.eihror.todo_list.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    JwtLogoutHandler(JwtService jwtService, UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            try {
                String email = jwtService.extractUsername(token);
                userRepository.findByEmail(email).ifPresent(u -> {
                    u.setToken(null);
                    userRepository.saveAndFlush(u);
                });
            } catch (Exception ignored) {}
        }
        SecurityContextHolder.clearContext();
    }

}
