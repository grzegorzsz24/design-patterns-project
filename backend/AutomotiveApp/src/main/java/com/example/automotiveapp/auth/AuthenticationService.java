package com.example.automotiveapp.auth;

import com.example.automotiveapp.config.jwt.JwtService;
import com.example.automotiveapp.domain.File;
import com.example.automotiveapp.domain.Role;
import com.example.automotiveapp.domain.RoleName;
import com.example.automotiveapp.domain.User.UserBuilder;
import com.example.automotiveapp.repository.FileRepository;
import com.example.automotiveapp.repository.RoleRepository;
import com.example.automotiveapp.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private static final int MAX_AGE = 7 * 24 * 60 * 60;
    private static final int EXPIRATION_DAYS = 7;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final FileRepository fileRepository;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        File file = new File();
        file.setFileUrl("default_profile_picture.jpg");
        Role userRole = roleRepository.findByName("USER").orElseGet(() -> {
            // start L1 Prototype - third usage
            return roleRepository.save(new Role(RoleName.USER_ROLE.clone()));
        });

        // start L1 Builder - first usage
        var user = new UserBuilder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .nickname(registerRequest.getNickname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(Set.of(userRole))
                .file(file)
                .dateOfBirth(registerRequest.getDateOfBirth())
                .publicProfile(true)
                .build();
        file.setUser(user);
        userRepository.save(user);
        fileRepository.save(file);
        return AuthenticationResponse.builder().build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        var authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        authenticationManager.authenticate(authentication);

        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        Cookie cookie = getCookie(jwtToken);
        response.addCookie(cookie);
        setHeaders(response, "jwt=" + jwtToken + "; Max-Age=" + (MAX_AGE) + "; Secure; HttpOnly; Path=/; SameSite=None");

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(EXPIRATION_DAYS).truncatedTo(ChronoUnit.SECONDS);
        String resourceUrl = "http://localhost:8080/images/" + user.getFile().getFileUrl();
        return AuthenticationResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(resourceUrl)
                .cookieExpirationDate(expirationDate.toString())
                .userId(String.valueOf(user.getId()))
                .publicProfile(user.isPublicProfile())
                .role(user.getRoles().stream().findFirst().get().getRoleName().getName())
                .build();
    }

    private Cookie getCookie(String jwtToken) {
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setMaxAge(MAX_AGE);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    private void setHeaders(HttpServletResponse response, String cookieValue) {
        response.addHeader("Set-Cookie", cookieValue);
        response.setHeader("Access-Control-Allow-Headers", "Date, Content-Type, Accept, X-Requested-With, Authorization, From, X-Auth-Token, Request-Id");
        response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }
}
