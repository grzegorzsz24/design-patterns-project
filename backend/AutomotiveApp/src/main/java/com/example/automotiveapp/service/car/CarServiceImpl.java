package com.example.automotiveapp.service.car;

import com.example.automotiveapp.dto.CarDto;
import com.example.automotiveapp.exception.ResourceNotFoundException;
import com.example.automotiveapp.mapper.CarDtoMapper;
import com.example.automotiveapp.repository.car.CarRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CarServiceImpl extends CarService {

    public CarServiceImpl(CarRepository carRepository) {
        super(carRepository);
    }

    @Override
    public List<CarDto> getCars() {
        return carRepository.findAll().stream()
                .map(CarDtoMapper::map)
                .toList();
    }

    @Override
    public List<String> getBrands() {
        return carRepository.findDistinctBrandsOrderedAlphabetically();
    }

    @Override
    public List<String> getCarModels(String carBrand) {
        carRepository.findByBrand(carBrand)
                .orElseThrow(() -> new ResourceNotFoundException("Nie znaleziono samochodu"));
        return carRepository.findBrandsByModelOrderedAlphabetically(carBrand);
    }

    @Override
    public Map<String, List<String>> getBrandsWithModels() {
        List<String> brands = carRepository.findDistinctBrandsOrderedAlphabetically();
        Map<String, List<String>> brandsWithModels = new HashMap<>();

        for (String brand : brands) {
            List<String> models = carRepository.findBrandsByModelOrderedAlphabetically(brand);
            brandsWithModels.put(brand, models);
        }
        Map<String, List<String>> sortedBrandsWithModels = new TreeMap<>(Comparator.naturalOrder());
        sortedBrandsWithModels.putAll(brandsWithModels);

        return sortedBrandsWithModels;
    }
}