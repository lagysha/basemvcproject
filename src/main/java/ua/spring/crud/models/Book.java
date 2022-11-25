package ua.spring.crud.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Enter title longer than 1 character")
    @Size(min = 1,max = 200,message = "Invalid size of name")
    @Column(name = "title")
    private String title;
    @NotNull(message = "Enter author of the book")
    @Size(min = 2,max = 100,message = "Invalid size of name")
    @Column(name = "author")
    private String author;
    @Min(value = 1500,message = "Year must be greater than 1500")
    @Column(name = "age")
    private int age;
    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken_at;

    @Transient
    private boolean expired;

    @ManyToOne()
    @JoinColumn(name = "person_id",referencedColumnName = "id")
    private Person owner;


    public Book(){}

    public Book(String title, String author, int age) {
        this.title = title;
        this.author = author;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(Date taken_at) {
        this.taken_at = taken_at;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && age == book.age && Objects.equals(title, book.title) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, age);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", age=" + age +
                '}';
    }
}
