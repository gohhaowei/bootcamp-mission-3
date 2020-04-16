package com.btkbootcamp.mission3;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/cars")

public class CarController {

   private CarRepository carRepository;

    // Create a new car
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car create(@RequestBody Car car){
        carRepository.save(car);
        return car;
    }


}
