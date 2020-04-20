package com.btkbootcamp.mission3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")

public class CarController {

    @Autowired
    private CarRepository carRepository;

    // Create a new car
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody Car car){
        carRepository.save(car);
        return car;
    }

    // Get all cars
    @GetMapping
    public Iterable getAllCars(){
        return carRepository.findAll();
    }

    // Get car by Id
    @GetMapping ("/{id}")
    public Optional<Car> getCarById(@PathVariable int id){
        return carRepository.findById(id);
    }

    // Delete car by Id
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCarById(@PathVariable int id){
        carRepository.deleteById(id);
    }

    // Update car by Id
    @PutMapping ("/{id}")
    public Car updateCarById(@PathVariable int id, @RequestBody Car newCar){
        // First, find the existing car by Id. If the car does not exists,
        // then we will create a new record
        Optional<Car> searchResult = carRepository.findById(id);

        // If the car exists, we shall replace the old car record with the new one
        if (searchResult.isPresent()){
            Car existingCar = searchResult.get();
            existingCar.setBrand(newCar.getBrand());
            existingCar.setModel(newCar.getModel());
            existingCar.setYear((newCar.getYear()));
            carRepository.save(existingCar);
            return existingCar;
        }
        else {
            carRepository.save(newCar);
            return newCar;
        }

    }


}
