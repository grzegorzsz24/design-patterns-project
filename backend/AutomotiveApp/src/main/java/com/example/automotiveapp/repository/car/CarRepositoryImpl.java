package com.example.automotiveapp.repository.car;

import com.example.automotiveapp.domain.Car;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CarRepositoryImpl implements CarRepository {

    private final  CarJpaRepository carJpaRepository;

    @Override
    public Optional<Car> findByModelAndBrand(String model, String brand) {
        return carJpaRepository.findByModelAndBrand(model, brand);
    }

    @Override
    public List<String> findDistinctBrandsOrderedAlphabetically() {
        return carJpaRepository.findDistinctBrandsOrderedAlphabetically();
    }

    @Override
    public List<String> findBrandsByModelOrderedAlphabetically(String brand) {
        return carJpaRepository.findBrandsByModelOrderedAlphabetically(brand);
    }

    @Override
    public Optional<Car> findByBrand(String brand) {
        return carJpaRepository.findByBrand(brand);
    }

    @Override
    public List<Car> findAll() {
        return carJpaRepository.findAll();
    }
}
