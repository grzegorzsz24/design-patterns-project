package com.example.automotiveapp.service.article;

// L5 Interface Segregation - first impl
public interface ArticlePersistenceService extends DeleteArticleByIdService, SaveArticleService, SetArticleApprovedService {
}
