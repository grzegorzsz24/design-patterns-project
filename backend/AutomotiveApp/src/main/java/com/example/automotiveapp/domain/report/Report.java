package com.example.automotiveapp.domain.report;

import com.example.automotiveapp.domain.ReportType;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reportTypeId;
    @Embedded
    @AttributeOverride(name = "type", column = @Column(name = "report_type", nullable = false))
    private ReportType reportType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean isRead;
}

