package jdbc.template.app.dao;

import jdbc.template.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.sql.*;
import java.util.*;

@Component
public class PersonDao {

    private Connection connection;
    private JdbcTemplate jdbcTemplate;
    private PersonMapper personMapper;

    @Autowired
    PersonDao(JdbcTemplate jdbcTemplate, PersonMapper personMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.personMapper = personMapper;
    }


    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM people;", personMapper);
    }

    public Person findPerson(int id){
        return jdbcTemplate.query("SELECT * FROM people WHERE id=?;", new Object[] {id}, personMapper).stream().findAny().orElse(null);
    }


    public void save(Person person){
        jdbcTemplate.update("INSERT INTO people (name, age, email) VALUES (?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person person){
        jdbcTemplate.update("UPDATE people SET name=?, age=?, email=? WHERE id=?;", person.getName(), person.getAge(), person.getEmail(), id);
    }

    public void delete(int id){
        jdbcTemplate.update("DELETE FROM people WHERE id = ?;", id);
    }

}
