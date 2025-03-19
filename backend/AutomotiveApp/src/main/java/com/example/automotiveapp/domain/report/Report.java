package com.example.automotiveapp.domain.report;

import com.example.automotiveapp.domain.ReportType;
import com.example.automotiveapp.domain.User.User;
import jakarta.persistence.*;
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

