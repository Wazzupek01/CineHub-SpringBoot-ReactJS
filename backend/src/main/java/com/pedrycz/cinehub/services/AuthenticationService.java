package com.pedrycz.cinehub.services;

import com.pedrycz.cinehub.exceptions.RoleNotFoundException;
import com.pedrycz.cinehub.model.dto.user.UserLoginDTO;
import com.pedrycz.cinehub.model.dto.user.UserRegisterDTO;
import com.pedrycz.cinehub.model.entities.Role;
import com.pedrycz.cinehub.model.entities.User;
import com.pedrycz.cinehub.repositories.RoleRepository;
import com.pedrycz.cinehub.repositories.UserRepository;
import com.pedrycz.cinehub.security.AuthenticationResponse;
import com.pedrycz.cinehub.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserRegisterDTO request) {

        Role role = roleRepository.findRoleByName("ROLE_USER").orElseThrow(() -> new RoleNotFoundException("ROLE_USER"));
        
        User user = User.builder()
                .nickname(request.nickname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .build();
        
            userRepository.save(user);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("ROLE", user.getRole());
            String jwtToken = jwtService.generateToken(extraClaims, user);
            
            log.info("Registered user: {}", user.getNickname());
            return new AuthenticationResponse(jwtToken, user.getNickname(), user.getRole().getName());
    }

    public AuthenticationResponse authenticate(UserLoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(), request.password()
                )
            );

        User user =  userRepository.findUserByEmail(request.email()).orElseThrow(() -> new UsernameNotFoundException(request.email()));
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("ROLE", user.getRole());
        String jwtToken = jwtService.generateToken(extraClaims, user);
        
        log.info("Authenticated user: {}", user.getNickname());
        return new AuthenticationResponse(jwtToken, user.getNickname(), user.getRole().getName());
    }
}
