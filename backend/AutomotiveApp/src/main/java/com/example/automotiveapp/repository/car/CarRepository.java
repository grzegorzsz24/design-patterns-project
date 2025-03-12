package com.example.automotiveapp.repository.car;

import com.example.automotiveapp.domain.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    Optional<Car> findByModelAndBrand(String model, String brand);

    List<String> findDistinctBrandsOrderedAlphabetically();

    List<String> findBrandsByModelOrderedAlphabetically(String brand);

    Optional<Car> findByBrand(String brand);

    List<Car> findAll();
}
