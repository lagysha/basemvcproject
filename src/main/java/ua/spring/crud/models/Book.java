package ua.spring.crud.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @NotNull(message = "Enter title longer than 1 character")
    @Size(min = 1,max = 200,message = "Invalid size of name")
    private String title;
    @NotNull(message = "Enter author of the book")
    @Size(min = 2,max = 100,message = "Invalid size of name")
    private String author;
    @Min(value = 1500,message = "Year must be greater than 1500")
    private int age;

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

}
