package com.example.automotiveapp.service.search;

import com.example.automotiveapp.dto.SearchResultsDto;

// L2 Bridge - first implementation
public interface SearchService {

    SearchResultsDto search(String keyword);
}
