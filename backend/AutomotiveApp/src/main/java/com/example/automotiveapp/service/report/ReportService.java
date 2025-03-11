package com.example.automotiveapp.service.report;

import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

// L2 Bridge - first implementation
@RequiredArgsConstructor
public abstract class ReportService {
    protected final ReportRepository reportRepository;

    public abstract List<ReportDto> findReports(String reportType);

    public abstract void deleteReportById(Long reportId);

    public abstract ReportDto setReportAsRead(Long reportId);
}
