package com.example.automotiveapp.service.report;

import com.example.automotiveapp.domain.report.Report;
import com.example.automotiveapp.domain.ReportType;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.ReportDtoMapper;
import com.example.automotiveapp.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl extends ReportService {

    public ReportServiceImpl(ReportRepository reportRepository) {
        super(reportRepository);
    }

    @Override
    public List<ReportDto> findReports(String reportType) {
        return reportRepository.findAllByReportType(ReportType.valueOf(reportType))
                .stream()
                .map(ReportDtoMapper::map)
                .toList();
    }

    @Override
    public void deleteReportById(Long reportId) {
        reportRepository.deleteById(reportId);
    }

    @Override
    public ReportDto setReportAsRead(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono raportu"));
        report.setRead(true);
        return ReportDtoMapper.map(reportRepository.save(report));
    }
}
