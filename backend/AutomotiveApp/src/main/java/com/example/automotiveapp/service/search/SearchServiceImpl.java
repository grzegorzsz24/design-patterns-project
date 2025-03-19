package com.example.automotiveapp.service.search;

import com.example.automotiveapp.aspect.RateLimit;
import com.example.automotiveapp.dto.ArticleDto;
import com.example.automotiveapp.dto.EventDto;
import com.example.automotiveapp.dto.ForumDto;
import com.example.automotiveapp.dto.PostDto;
import com.example.automotiveapp.dto.SearchResultsDto;
import com.example.automotiveapp.dto.UserDto;
import com.example.automotiveapp.mapper.ArticleDtoMapper;
import com.example.automotiveapp.mapper.EventDtoMapper;
import com.example.automotiveapp.mapper.ForumDtoMapper;
import com.example.automotiveapp.mapper.PostDtoMapper;
import com.example.automotiveapp.mapper.UserDtoMapper;
import com.example.automotiveapp.repository.ArticleRepository;
import com.example.automotiveapp.repository.EventRepository;
import com.example.automotiveapp.repository.ForumRepository;
import com.example.automotiveapp.repository.PostRepository;
import com.example.automotiveapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


// L2 Facade - first implementation
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private static final int CALLS = 100;
    private static final int DURATION = 60000;

    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final ArticleRepository articleRepository;
    private final PostRepository postRepository;
    private final EventRepository eventRepository;

    @RateLimit(calls = CALLS, duration = DURATION)
    public SearchResultsDto search(String keyword) {
        SearchResultsDto searchResultsDto = new SearchResultsDto();
        searchResultsDto.setUsers(searchUsers(keyword));
        searchResultsDto.setForums(searchForums(keyword));
        searchResultsDto.setArticles(searchArticles(keyword));
        searchResultsDto.setPosts(searchPosts(keyword));
        searchResultsDto.setEvents(searchEvents(keyword));

        return searchResultsDto;
    }

    public List<UserDto> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword).stream()
                .map(UserDtoMapper::map)
                .toList();
    }

    public List<ForumDto> searchForums(String keyword) {
        return forumRepository.findAllByTitleContainsIgnoreCaseOrderByCreatedAtDesc(
                        keyword)
                .stream()
                .map(ForumDtoMapper::map)
                .toList();
    }

    public List<ArticleDto> searchArticles(String keyword) {
        return articleRepository.findAllByTitleContainsIgnoreCase(keyword)
                .stream()
                .map(ArticleDtoMapper::map)
                .toList();
    }

    public List<PostDto> searchPosts(String keyword) {
        return postRepository.findAllByContentContainsIgnoreCaseOrderByPostedAtDesc(
                        keyword)
                .stream()
                .map(PostDtoMapper::map)
                .toList();
    }

    public List<EventDto> searchEvents(String keyword) {
        return eventRepository.findAllByTitleContainsIgnoreCaseOrderByEventDateAsc(
                        keyword)
                .stream()
                .map(EventDtoMapper::map)
                .toList();
    }
}