package weather.forecast.app.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weather.forecast.app.security.dto.LoginRequest;
import weather.forecast.app.security.dto.RegisterRequest;
import weather.forecast.app.user.User;
import weather.forecast.app.user.UserRepository;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, HttpSession session) {
        if (userRepository.existsByUsername(request.getUsername()))
            return ResponseEntity.badRequest().body(Map.of("message", "Nazwa użytkownika jest już zajęta"));
        if (userRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body(Map.of("message", "Email jest już zarejestrowany"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        // Auto-login po rejestracji
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
    }
}