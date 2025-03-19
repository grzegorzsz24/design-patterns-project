package com.example.automotiveapp.dto;

public record FiltersDto(
        String title,
        String carBrand,
        String carModel,
        int page,
        int size
) {
}
