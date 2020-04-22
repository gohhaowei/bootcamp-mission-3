package com.btkbootcamp.mission3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;


@Repository
public class CarRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Car save(Car car) {

        //SimpleJdbcInsert is used to easily insert into the table, and fetch the auto generated ID column
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("car").usingGeneratedKeyColumns("id");

        //Set the column names and values to be inserted into the table
        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("brand", car.getBrand());
        parameters.put("model", car.getModel());
        parameters.put("year", car.getYear());

        //Execute the SQL insert, and fetch the auto generated ID
        int generatedId = simpleJdbcInsert.executeAndReturnKey(parameters).intValue();

        //Update the original car object with the new generated ID and return it back
        car.setId(generatedId);

        return car;
    }

    public Iterable<Car> findAll(){

        String sql = "SELECT * FROM car";
        Iterable<Car> result = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Car.class));
        return result;

    }

    public Car findById(int id){

        String sql = "SELECT * FROM car WHERE id = ?";

        try {
            Object[] parameters = new Object[]{id};
            Car result = jdbcTemplate.queryForObject(sql, parameters, BeanPropertyRowMapper.newInstance(Car.class));
            return result;
        }
        // If id is not found, then we throw our custom exception CarNotFoundException
        catch (EmptyResultDataAccessException e) {
            throw (new CarNotFoundException(id));
        }

    }

    public void deleteById(int id){

        String sql = "DELETE FROM car WHERE id = ?";
        Object[] parameters = new Object[]{id};
        jdbcTemplate.update(sql, parameters);
        return;

    }

    public Car updateCarById(int id, Car car){

        String sql = "UPDATE car SET brand = ?, model = ?, year = ? WHERE id = ?";
        Object[] parameters = new Object[]{car.getBrand(), car.getModel(), car.getYear(), id};

        // We need to fetch the number of rows updated so
        // that we can know whether an original record by the same Id was updated.
        int rowsUpdated = jdbcTemplate.update(sql, parameters);

        // If no records were updated, then we shall insert a new record.
        if(rowsUpdated == 0){
            return save(car);
        }
        // Else, we can return te updated car along.
        else {
            car.setId(id);
            return car;
        }

    }

}
