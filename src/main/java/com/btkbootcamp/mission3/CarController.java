package com.btkbootcamp.mission3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")

public class CarController {

    @Autowired
    private CarRepository carRepository;

    // Create a new car
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@RequestBody Car car){

        Car savedCar = carRepository.save(car);

        return savedCar;
    }

    // Get all cars
    @GetMapping
    public Iterable getAllCars(){
        return carRepository.findAll();
    }

    // Get car by Id
    @GetMapping ("/{id}")
    public Car getCarById(@PathVariable int id){
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
       Car updatedCar = carRepository.updateCarById(id, newCar);
       return updatedCar;
    }


}
