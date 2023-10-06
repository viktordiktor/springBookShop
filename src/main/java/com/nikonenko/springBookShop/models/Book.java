package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Integer id_book;
    @NotEmpty
    @Size(min = 2, max = 120, message = "Name should be between 2 and 120 chars")
    @Column(name = "name")
    private String name;
    @NotEmpty
    @Size(min = 2, max = 120, message = "Author should be between 2 and 120 chars")
    @Column(name = "author")
    private String author;
    @NotEmpty
    @Min(value=1700, message = "Year in diapason between 1700 and 2023") @Max(value=2023, message = "Age in diapason between 1700 and 2023")
    @Column(name = "year")
    private Integer year;
    @NotEmpty
    @Min(value = 1, message = "Pages should be more than one!")
    @Column(name = "pages")
    private Integer pages;
    @NotEmpty
    @Size(min = 2, max = 70, message = "Genre should be between 2 and 70 chars")
    @Column(name = "genre")
    private String genre;
    @NotEmpty
    @Min(value = 1, message = "Price should be more than one!")
    @Column(name = "price")
    private Integer price;
    @NotEmpty
    @Min(value = 1, message = "Amount should be more than one!")
    @Column(name = "amount")
    private Integer amount;
    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private String image;

    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<Review> reviews;

    @ManyToMany
    @JoinTable(
            name= "book_order",
            joinColumns = @JoinColumn(name = "id_book"),
            inverseJoinColumns = @JoinColumn(name = "id_order")
    )
    private List<Order> orders;

    public Book(){
    }

    public Book(String name, String author, Integer year, Integer pages, String genre, Integer price,
                Integer amount, String image, List<Review> reviews, List<Order> orders) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.genre = genre;
        this.price = price;
        this.amount = amount;
        this.image = image;
        this.reviews = reviews;
        this.orders = orders;
    }

    @Transient
    public static Double getRating(List<Review> reviews){
        return reviews.stream()
                .mapToInt(Review::getReview)
                .average().orElse(0.0);
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


}
