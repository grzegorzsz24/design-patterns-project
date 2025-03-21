package com.example.automotiveapp.dto;

// L6 3 parameters - dto
public record FiltersDto(
        String title,
        String carBrand,
        String carModel,
        int page,
        int size
) {
}
