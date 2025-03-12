package com.example.automotiveapp.repository.message;

import com.example.automotiveapp.domain.message.ConcreteMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageJpaRepository extends JpaRepository<ConcreteMessage, Long> {
    List<ConcreteMessage> findAllByChannelId(Long channelId);
}