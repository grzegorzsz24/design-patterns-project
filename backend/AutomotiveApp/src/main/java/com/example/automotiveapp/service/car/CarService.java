package com.example.automotiveapp.service.car;

import com.example.automotiveapp.dto.CarDto;
import com.example.automotiveapp.repository.car.CarRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

// L2 Bridge - first implementation
// L5 Dependency Inversion - fifth impl
@RequiredArgsConstructor
public abstract class CarService {
    protected final CarRepository carRepository;

    public abstract List<CarDto> getCars();

    public abstract List<String> getBrands();

    public abstract List<String> getCarModels(String carBrand);

    public abstract Map<String, List<String>> getBrandsWithModels();
}
