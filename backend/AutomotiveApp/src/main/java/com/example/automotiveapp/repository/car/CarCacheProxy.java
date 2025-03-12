package com.example.automotiveapp.repository.car;

import com.example.automotiveapp.domain.Car;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

// L2 Proxy - first impl
public class CarCacheProxy implements CarRepository {

    private final CarRepository carRepository;
    private final HashMap<Long, Car> cache = new HashMap<>();

    public CarCacheProxy(CarRepository carRepository) {
        this.carRepository = carRepository;
        List<Car> cars = carRepository.findAll();
        cars.forEach(car -> cache.put(car.getId(), car));
    }

    @Override
    public Optional<Car> findByModelAndBrand(String model, String brand) {
        return cache.values().stream()
                .filter(c -> c.getBrand().equals(brand) && c.getModel().equals(model))
                .findFirst();
    }

    @Override
    public List<String> findDistinctBrandsOrderedAlphabetically() {
        return carRepository.findDistinctBrandsOrderedAlphabetically();
    }

    @Override
    public List<String> findBrandsByModelOrderedAlphabetically(String brand) {
        return carRepository.findBrandsByModelOrderedAlphabetically(brand);
    }

    @Override
    public Optional<Car> findByBrand(String brand) {
        return cache.values().stream()
                .filter(c -> c.getBrand().equals(brand))
                .findFirst();
    }

    @Override
    public List<Car> findAll() {
        return cache.values().stream().toList();
    }
}
