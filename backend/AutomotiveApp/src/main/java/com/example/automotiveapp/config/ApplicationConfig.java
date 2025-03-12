package com.example.automotiveapp.config;

import com.example.automotiveapp.repository.UserRepository;
import com.example.automotiveapp.repository.car.CarCacheProxy;
import com.example.automotiveapp.repository.car.CarJpaRepository;
import com.example.automotiveapp.repository.car.CarRepositoryImpl;
import com.example.automotiveapp.repository.message.MessageCacheProxy;
import com.example.automotiveapp.repository.message.MessageJpaRepository;
import com.example.automotiveapp.repository.message.MessageRepository;
import com.example.automotiveapp.repository.message.MessageRepositoryImpl;
import com.example.automotiveapp.repository.notification.NotificationCacheProxy;
import com.example.automotiveapp.repository.notification.NotificationJpaRepository;
import com.example.automotiveapp.repository.notification.NotificationRepository;
import com.example.automotiveapp.repository.notification.NotificationRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CarCacheProxy carCacheProxy(CarJpaRepository carJpaRepository) {
        return new CarCacheProxy(new CarRepositoryImpl(carJpaRepository));
    }

    @Bean
    public MessageRepository messageRepository(MessageJpaRepository messageJpaRepository) {
        return new MessageCacheProxy(new MessageRepositoryImpl(messageJpaRepository));
    }

    @Bean
    public NotificationRepository notificationRepository(NotificationJpaRepository notificationJpaRepository) {
        return new NotificationCacheProxy(new NotificationRepositoryImpl(notificationJpaRepository));
    }
}
