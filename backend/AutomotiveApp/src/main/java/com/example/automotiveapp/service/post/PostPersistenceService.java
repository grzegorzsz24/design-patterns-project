package com.example.automotiveapp.service.post;

// L5 Interface Segregation - third impl
public interface PostPersistenceService extends DeletePostService, SavePostService, UpdatePostService, ReportPostService {
}
