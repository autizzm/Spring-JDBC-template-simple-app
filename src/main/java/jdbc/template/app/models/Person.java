package jdbc.template.app.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message="name shouldn't be empty")
    @Size(min=2, max=30, message="Name should be between 2 and 30 characters")
    private String name;

    @Min(value=0, message="Age can't be negative")
    private int age;

    @NotEmpty(message="email shouldn't be empty")
    @Email(message="email must be valid")
    @Size(min=5, max = 30, message="email length must be between 5 and 30 characters")
    private String email;

    public Person(){

    }

    public Person(int id, String name, int age, String email){
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
