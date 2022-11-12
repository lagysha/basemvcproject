package ua.spring.crud.models;


import javax.validation.constraints.*;


public class Person {
    private int id;

    @Size(min = 2,max = 100,message = "Invalid size of name")
    @Pattern(regexp = "[A-Z][a-z]+? [A-Z][a-z]+?"
            ,message = "Write Full Name using this format:Name Surname")
    private  String fullName;

    @Pattern(regexp = "([0-2][0-9]|3[0-1])/(0[0-9]|1[0-2])/([0-9]{2})"
            ,message = "Write date of birth using this format: 18/09/22")
    private String dob;

    public Person(){}

    public Person(String fullName, String dob) {
        this.fullName = fullName;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + fullName + '\'' +
                ", address='" + dob + '\'' +
                '}';
    }
}
