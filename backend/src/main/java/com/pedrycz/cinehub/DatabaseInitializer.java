package com.pedrycz.cinehub;

import com.pedrycz.cinehub.model.entities.Role;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.repositories.MovieRepository;
import com.pedrycz.cinehub.repositories.RoleRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job initDatabaseJob;
    private final MovieRepository movieRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        long startTime = System.currentTimeMillis();
        
        if (movieRepository.count() == 0) {
            log.info("Initializing database...");
            JobParameters jobParameters = new JobParameters(
                    Map.of("time", new JobParameter<>(System.currentTimeMillis(), Long.class))
            );
            jobLauncher.run(initDatabaseJob, jobParameters);
        }

        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(new Role(1, "ROLE_ADMIN"), new Role(0, "ROLE_USER")));
        }

        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .nickname("Admin01")
                    .password(passwordEncoder.encode("Password!01"))
                    .email("admin01@gmail.com")
                    .role(roleRepository.findRoleByName("ROLE_ADMIN").orElse(new Role(1, "ROLE_ADMIN")))
                    .build()
            );
            
            userRepository.save(User.builder()
                    .nickname("TestUser01")
                    .password(passwordEncoder.encode("Password!01"))
                    .email("testuser01@gmail.com")
                    .role(roleRepository.findRoleByName("ROLE_USER").orElse(new Role(0, "ROLE_USER")))
                    .build()
            );
        }

        log.info("Database initialized in {}s", (System.currentTimeMillis() - startTime)/1000);
    }
}
