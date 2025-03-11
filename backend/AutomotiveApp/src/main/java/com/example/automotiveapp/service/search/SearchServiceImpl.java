package com.example.automotiveapp.service.search;

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
    private final UserRepository userRepository;
    private final ForumRepository forumRepository;
    private final ArticleRepository articleRepository;
    private final PostRepository postRepository;
    private final EventRepository eventRepository;

    public SearchResultsDto search(String keyword) {
        SearchResultsDto searchResultsDto = new SearchResultsDto();
        List<UserDto> users = userRepository.searchUsers(keyword).stream()
                .map(UserDtoMapper::map)
                .toList();
        searchResultsDto.setUsers(users);

        List<ForumDto> forums = forumRepository.findAllByTitleContainsIgnoreCaseOrderByCreatedAtDesc(keyword)
                .stream()
                .map(ForumDtoMapper::map)
                .toList();
        searchResultsDto.setForums(forums);

        List<ArticleDto> articles = articleRepository.findAllByTitleContainsIgnoreCase(keyword)
                .stream()
                .map(ArticleDtoMapper::map)
                .toList();
        searchResultsDto.setArticles(articles);

        List<PostDto> posts = postRepository.findAllByContentContainsIgnoreCaseOrderByPostedAtDesc(keyword)
                .stream()
                .map(PostDtoMapper::map)
                .toList();
        searchResultsDto.setPosts(posts);

        List<EventDto> events = eventRepository.findAllByTitleContainsIgnoreCaseOrderByEventDateAsc(keyword)
                .stream()
                .map(EventDtoMapper::map)
                .toList();
        searchResultsDto.setEvents(events);

        return searchResultsDto;
    }
}