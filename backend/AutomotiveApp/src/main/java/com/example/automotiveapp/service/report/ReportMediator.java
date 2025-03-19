package com.example.automotiveapp.service.report;

import com.example.automotiveapp.domain.ReportType;
import com.example.automotiveapp.domain.report.Report;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.BadRequestException;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.logging.Logger;
import com.example.automotiveapp.logging.LoggerFactory;
import com.example.automotiveapp.mapper.ReportDtoMapper;
import com.example.automotiveapp.repository.EventRepository;
import com.example.automotiveapp.repository.ForumRepository;
import com.example.automotiveapp.repository.PostRepository;
import com.example.automotiveapp.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

// L3 Mediator - second impl
@Service
@RequiredArgsConstructor
public class ReportMediator {
    private static final Logger logger = LoggerFactory.getInstance();

    private final ForumRepository forumRepository;
    private final EventRepository eventRepository;
    private final ReportRepository reportRepository;
    private final ReportDtoMapper reportDtoMapper;
    private final PostRepository postRepository;

    public ReportDto reportForum(ReportDto reportDto) {
        if (forumRepository.findById(reportDto.getReportTypeId()).isEmpty()) {
            throw new ResourceNotFoundException("Nie znaleziono forum");
        }
        Optional<Report> existingReport = reportRepository
                .findByReportTypeIdAndReportType(reportDto.getReportTypeId(), ReportType.FORUM_REPORT);
        if (existingReport.isPresent()) {
            throw new BadRequestException("Forum już zostało zgłoszone");
        }
        Report saved = reportRepository.save(reportDtoMapper.map(reportDto));
        return ReportDtoMapper.map(saved);
    }

    public ReportDto reportPost(ReportDto reportDto) {
        if (postRepository.findById(reportDto.getReportTypeId()).isEmpty()) {
            throw new ResourceNotFoundException("Nie znaleziono postu");
        }
        Optional<Report> report = reportRepository
                .findByReportTypeIdAndReportType(reportDto.getReportTypeId(), ReportType.POST_REPORT);
        if (report.isPresent()) {
            throw new BadRequestException("Post już został zgłoszony");
        }

        return ReportDtoMapper.map(reportRepository.save(reportDtoMapper.map(reportDto)));
    }

    public ReportDto reportEvent(ReportDto reportDto) {
        logger.log("Reporting event: " + reportDto.getId());
        if (eventRepository.findById(reportDto.getReportTypeId()).isEmpty()) {
            logger.error("Report type not found");
            throw new ResourceNotFoundException("Nie znaleziono wydarzenia");
        }
        Optional<Report> report = reportRepository
                .findByReportTypeIdAndReportType(reportDto.getReportTypeId(), ReportType.EVENT_REPORT);
        if (report.isPresent()) {
            logger.log("Report found");
            throw new BadRequestException("Wydarzenie już zostało zgłoszone");
        }

        return ReportDtoMapper.map(reportRepository.save(reportDtoMapper.map(reportDto)));
    }
}
