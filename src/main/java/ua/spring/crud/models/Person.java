package ua.spring.crud.models;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Size(min = 2,max = 100,message = "Invalid size of name")
    @Pattern(regexp = "[A-Z][a-z]+? [A-Z][a-z]+?"
            ,message = "Write Full Name using this format:Name Surname")
    @Column(name = "full_name")
    private  String fullName;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern= "yyyy-MM-dd")
    private Date dob;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){}

    public Person(String fullName, Date dob) {
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(fullName, person.fullName) && Objects.equals(dob, person.dob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, dob);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + fullName + '\'' +
                ", date_of_birth='" + dob + '\'' +
                '}';
    }
}
