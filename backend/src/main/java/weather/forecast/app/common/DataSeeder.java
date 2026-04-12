package weather.forecast.app.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import weather.forecast.app.user.User;
import weather.forecast.app.user.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.password:admin123}") // hasło można nadpisać zmienną środowiskową
    private String adminPassword;

    @Override
    public void run(String... args) {
    // Tworzy domyślnego admina przy starcie aplikacji, jeśli jeszcze nie istnieje
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(User.builder()
                    .username("admin")
                    .email("admin@weatherapp.com")
                    .passwordHash(passwordEncoder.encode(adminPassword))
                    .role("ADMIN")
                    .build());
            log.info("Admin user seeded");
        }
    }
}