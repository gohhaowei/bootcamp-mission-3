package com.btkbootcamp.mission3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;


@Repository

public class CarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Car save(Car car) {

        String sql = "INSERT INTO car (brand, model, year) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Here, we use jdbcTemplate's update method to return the generated key from database (i.e. AUTO_INCREMENT column)
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, car.getBrand());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getYear());
            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();

        // The generated key from database is updated back into car
        // so that we can return the new car object together with the generated Id
        car.setId(generatedId);

        return car;

    }


    public Iterable<Car> findAll(){

        String sql = "SELECT id, brand, model, year FROM car";
        Iterable<Car> result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Car.class));
        return result;

    }

    public Car findById(int id){

        String sql = "SELECT id, brand, model, year FROM car WHERE id = ?";
        Car result;

        try {
            Object[] parameters = new Object[]{id};
            result = jdbcTemplate.queryForObject(sql, parameters, BeanPropertyRowMapper.newInstance(Car.class));
        }
        // If id is not found, then we throw our custom exception CarNotFoundException
        catch (EmptyResultDataAccessException e) {
            throw new CarNotFoundException("Item not found: " + id);
        }

        return result;

    }

    public void deleteById(int id){

        String sql = "DELETE FROM car WHERE id = ?";
        Object[] parameters = new Object[]{id};
        int rowsDeleted = jdbcTemplate.update(sql, parameters);

        // If no records were deleted, then we know that the Id does not exists.
        // Therefore, error is returned.
        if (rowsDeleted == 0){
            throw new CarNotFoundException("No item found to delete: " + id);
        }
        return;

    }

    public Car updateCarById(int id, Car car){

        String sql = "UPDATE car SET brand = ?, model = ?, year = ? WHERE id = ?";
        Object[] parameters = new Object[]{car.getBrand(), car.getModel(), car.getYear(), id};

        // We need to fetch the number of rows updated so
        // that we can know whether the update actually happened.
        int rowsUpdated = jdbcTemplate.update(sql, parameters);

        // If no records were updated, then we know that the Id does not exists.
        // Therefore, error is returned.
        if(rowsUpdated == 0){
            throw new CarNotFoundException("No item found to be updated: " + id);
        }
        // Else, we can return the updated car.
        else {
            car.setId(id);
            return car;
        }

    }

}
