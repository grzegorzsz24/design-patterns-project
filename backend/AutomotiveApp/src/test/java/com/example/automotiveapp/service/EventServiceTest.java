package com.example.automotiveapp.service;

import com.example.automotiveapp.domain.Event;
import com.example.automotiveapp.domain.File;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.dto.EventDto;
import com.example.automotiveapp.dto.ReportDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.EventDtoMapper;
import com.example.automotiveapp.repository.EventRepository;
import com.example.automotiveapp.repository.FileRepository;
import com.example.automotiveapp.service.report.ReportMediator;
import com.example.automotiveapp.storage.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    EventRepository eventRepository;
    @Mock
    EventDtoMapper eventDtoMapper;
    @Mock
    FileStorageService fileStorageService;
    @Mock
    FileRepository fileRepository;
    @Mock
    ReportMediator reportMediator;

    @InjectMocks
    EventService eventService;

    Event event;
    EventDto eventDto;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        File file = new File();

        event = new Event();
        event.setId(1L);
        event.setUser(user);
        event.setImage(file);

        eventDto = new EventDto();
        eventDto.setId(1L);
    }

    @Test
    @DisplayName("Should save new event when valid data is provided")
    void should_SaveNewEvent_When_ValidDataProvided() {
        // given
        eventDto.setImageUrl("ImageUrl");
        when(eventDtoMapper.map(eventDto)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);

        try (MockedStatic<EventDtoMapper> mockedStatic = Mockito.mockStatic(EventDtoMapper.class)) {
            mockedStatic.when(() -> EventDtoMapper.map(event)).thenReturn(eventDto);

            // when
            EventDto savedDto = eventService.saveEvent(eventDto);

            // then
            assertNotNull(savedDto);
            verify(eventRepository, times(1)).save(event);
            verify(fileRepository, times(1)).saveAll(any());
        }
    }

    @Test
    @DisplayName("Should return EventDto when event exists")
    void should_ReturnEventDto_When_EventExists() {
        // given
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        try (MockedStatic<EventDtoMapper> mockedMapper = Mockito.mockStatic(EventDtoMapper.class)) {
            mockedMapper.when(() -> EventDtoMapper.map(event)).thenReturn(eventDto);

            // when
            Optional<EventDto> result = eventService.getEventById(eventId);

            // then
            assertTrue(result.isPresent());
            assertEquals(eventId, result.get().getId());
            verify(eventRepository, times(1)).findById(eventId);
        }
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when event does not exist")
    void should_ThrowResourceNotFoundException_When_EventDoesNotExist() {
        // given
        Long eventId = 999L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> eventService.getEventById(eventId));
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    @DisplayName("Should delete event and image when event exists")
    void should_DeleteEventAndImage_When_EventExists() {
        // given
        Long eventId = 1L;
        String imageName = "testImageName.png";
        EventDto eventDtoToDelete = new EventDto();
        eventDtoToDelete.setUser(1L);
        eventDtoToDelete.setId(eventId);
        eventDtoToDelete.setImageUrl("http://localhost:8080/images/" + imageName);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        try (MockedStatic<EventDtoMapper> mockedMapper = Mockito.mockStatic(EventDtoMapper.class)) {
            mockedMapper.when(() -> EventDtoMapper.map(event)).thenReturn(eventDtoToDelete);

            // when
            eventService.deleteEvent(eventId);

            // then
            verify(fileStorageService, times(1)).deleteFile(imageName);
            verify(eventRepository, times(1)).deleteById(eventId);
        }
    }

    @Test
    @DisplayName("Should call report mediator when ReportDto is provided")
    void should_CallReportMediator_When_ReportDtoProvided() {
        // given
        ReportDto reportDto = new ReportDto();
        when(reportMediator.reportEvent(reportDto)).thenReturn(reportDto);

        // when
        ReportDto result = eventService.reportEvent(reportDto);

        // then
        assertNotNull(result);
        verify(reportMediator, times(1)).reportEvent(reportDto);
    }
}
