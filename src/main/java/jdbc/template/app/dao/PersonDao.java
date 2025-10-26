package jdbc.template.app.dao;

import jdbc.template.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

    ////////////////////////
    ///Batch update tasting
    ///////////////////////////

    public void testMultipleUpdate(){
        List<Person> people = createPeople();
        System.out.println("started update");
        long before = System.currentTimeMillis();
        for(Person person : people) {
            jdbcTemplate.update("INSERT INTO people (name, age, email) VALUES (?, ?, ?);", person.getName(), person.getAge(), person.getEmail());
        }
        long timeTook = System.currentTimeMillis() - before;
        System.out.println("Multiple update took " + timeTook + " milliseconds");
    }

    public void testBatchUpdate(){
        List<Person> people = createPeople();
        long before = System.currentTimeMillis();
        System.out.println("started update");
        jdbcTemplate.batchUpdate("INSERT INTO people (name, age, email) VALUES (?, ?, ?);", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // i - итерируется по всему размеру batch, т.е. тут от 0 до 1000
                ps.setString(1, people.get(i).getName());
                ps.setInt(2, people.get(i).getAge());
                ps.setString(3, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return 1000;
            }
        });
        long timeTook = System.currentTimeMillis() - before;
        System.out.println("Multiple update took " + timeTook + " milliseconds");
    }

    private List<Person> createPeople(){
        List<Person> people = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            people.add(new Person(0, "test-person-" + i, 18, String.format("test_email_%03d@gmail.com", i)));
        }
        return people;
    }

    public void deleteTestPeople(){
        jdbcTemplate.update("DELETE FROM people WHERE name LIKE 'test%';");
    }
}
