package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Integer id_review;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_book", referencedColumnName = "id_book")
    private Book book;

    @Min(value = 0, message="Review should be in diaposon from 0 to 5")
    @Max(value = 5, message="Review should be in diaposon from 0 to 5")
    private Integer review;

    public Review(){}

    public Review(User user, Book book, Integer review) {
        this.user = user;
        this.book = book;
        this.review = review;
    }

    public Integer getId_review() {
        return id_review;
    }

    public void setId_review(Integer id_review) {
        this.id_review = id_review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }
}
