package com.example.automotiveapp.service.search;

import com.example.automotiveapp.domain.Event;
import com.example.automotiveapp.domain.Post;
import com.example.automotiveapp.domain.User.User;
import com.example.automotiveapp.domain.article.ApprovedArticleFactory;
import com.example.automotiveapp.domain.article.Article;
import com.example.automotiveapp.domain.forum.Forum;
import com.example.automotiveapp.dto.*;
import com.example.automotiveapp.mapper.*;
import com.example.automotiveapp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {

    private final String keyword = "test";
    @Mock
    UserRepository userRepository;
    @Mock
    ForumRepository forumRepository;
    @Mock
    ArticleRepository articleRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    EventRepository eventRepository;
    @InjectMocks
    SearchServiceImpl searchService;
    User user;
    Forum forum;
    Article article;
    Post post;
    Event event;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        forum = new Forum();
        forum.setId(2L);

        article = new ApprovedArticleFactory().createArticle(
                3L, "Test Title", "Test Content",
                LocalDateTime.now(), false, 0,
                new User(), true
        );


        post = new Post();
        post.setId(4L);

        event = new Event();
        event.setId(5L);
    }

    @Test
    @DisplayName("Should return mapped UserDto list when searching users")
    void should_ReturnUserDtos_When_SearchingUsers() {
        when(userRepository.searchUsers(keyword)).thenReturn(List.of(user));
        try (MockedStatic<UserDtoMapper> mocked = mockStatic(UserDtoMapper.class)) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            mocked.when(() -> UserDtoMapper.map(user)).thenReturn(userDto);

            List<UserDto> result = searchService.searchUsers(keyword);

            assertEquals(1, result.size());
            assertEquals(user.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should return mapped ForumDto list when searching forums")
    void should_ReturnForumDtos_When_SearchingForums() {
        when(forumRepository.findAllByTitleContainsIgnoreCaseOrderByCreatedAtDesc(keyword)).thenReturn(List.of(forum));
        try (MockedStatic<ForumDtoMapper> mocked = mockStatic(ForumDtoMapper.class)) {
            ForumDto dto = new ForumDto();
            dto.setId(forum.getId());
            mocked.when(() -> ForumDtoMapper.map(forum)).thenReturn(dto);

            List<ForumDto> result = searchService.searchForums(keyword);

            assertEquals(1, result.size());
            assertEquals(forum.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should return mapped ArticleDto list when searching articles")
    void should_ReturnArticleDtos_When_SearchingArticles() {
        when(articleRepository.findAllByTitleContainsIgnoreCase(keyword)).thenReturn(List.of(article));
        try (MockedStatic<ArticleDtoMapper> mocked = mockStatic(ArticleDtoMapper.class)) {
            ArticleDto dto = new ArticleDto();
            dto.setId(article.getId());
            mocked.when(() -> ArticleDtoMapper.map(article)).thenReturn(dto);

            List<ArticleDto> result = searchService.searchArticles(keyword);

            assertEquals(1, result.size());
            assertEquals(article.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should return mapped PostDto list when searching posts")
    void should_ReturnPostDtos_When_SearchingPosts() {
        when(postRepository.findAllByContentContainsIgnoreCaseOrderByPostedAtDesc(keyword)).thenReturn(List.of(post));
        try (MockedStatic<PostDtoMapper> mocked = mockStatic(PostDtoMapper.class)) {
            PostDto dto = new PostDto();
            dto.setId(post.getId());
            mocked.when(() -> PostDtoMapper.map(post)).thenReturn(dto);

            List<PostDto> result = searchService.searchPosts(keyword);

            assertEquals(1, result.size());
            assertEquals(post.getId(), result.get(0).getId());
        }
    }

    @Test
    @DisplayName("Should return mapped EventDto list when searching events")
    void should_ReturnEventDtos_When_SearchingEvents() {
        when(eventRepository.findAllByTitleContainsIgnoreCaseOrderByEventDateAsc(keyword)).thenReturn(List.of(event));
        try (MockedStatic<EventDtoMapper> mocked = mockStatic(EventDtoMapper.class)) {
            EventDto dto = new EventDto();
            dto.setId(event.getId());
            mocked.when(() -> EventDtoMapper.map(event)).thenReturn(dto);

            List<EventDto> result = searchService.searchEvents(keyword);

            assertEquals(1, result.size());
            assertEquals(event.getId(), result.get(0).getId());
        }
    }
}
