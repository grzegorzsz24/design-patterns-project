package com.example.automotiveapp.domain;

import jakarta.persistence.Embeddable;

// L2 Flyweight - first impl
@Embeddable
public record ReportType(String type) {

    public static final ReportType POST_REPORT = new ReportType("POST_REPORT");
    public static final ReportType FORUM_REPORT = new ReportType("FORUM_REPORT");
    public static final ReportType EVENT_REPORT = new ReportType("EVENT_REPORT");


    public static ReportType valueOf(String type) {
        return new ReportType(type);
    }
}
