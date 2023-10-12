package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "single_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer id_order;


    @Size(min = 2, max = 100, message = "Address should be between 2 and 70 chars")
    @Column(name = "address")
    private String address;


    @Size(min = 2, max = 100, message = "City should be between 2 and 70 chars")
    @Column(name = "city")
    private String city;


    @Size(min = 2, max = 100, message = "Phone should be between 2 and 70 chars")
    @Column(name = "phone")
    private String phone;


    @Size(min = 2, max = 100, message = "Status should be between 2 and 70 chars")
    @Column(name = "status")
    private String status;


    @Min(value = 1, message = "Postmail should be more than one!")
    @Column(name = "post_mail")
    private Integer post_mail;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @ManyToMany(mappedBy = "orders")
    private List<Book> books;

    public Order(){}

    public Order(String address, String city, String phone, String status,
                 Integer post_mail, User user, List<Book> books) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.status = status;
        this.post_mail = post_mail;
        this.user = user;
        this.books = books;
    }

    public Integer getId_order() {
        return id_order;
    }

    public void setId_order(Integer id_order) {
        this.id_order = id_order;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPost_mail() {
        return post_mail;
    }

    public void setPost_mail(Integer post_mail) {
        this.post_mail = post_mail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
