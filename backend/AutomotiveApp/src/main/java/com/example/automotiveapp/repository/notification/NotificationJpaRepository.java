package com.example.automotiveapp.repository.notification;

import com.example.automotiveapp.domain.notification.ConcreteNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<ConcreteNotification, Long> {
    List<ConcreteNotification> findAllByRecevierEmail(String email);
}
