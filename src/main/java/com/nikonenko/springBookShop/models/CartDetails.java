package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_details")
public class CartDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_details")
    private Integer id_details;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_book", referencedColumnName = "id_book")
    private Book book;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user")
    private Cart cart;

    @Column(name="amount")
    private Integer amount;

    public CartDetails(){}

    public CartDetails(Book book, Cart cart, Integer amount) {
        this.book = book;
        this.cart = cart;
        this.amount = amount;
    }

    public CartDetails(Integer id_details, Book book, Cart cart, Integer amount) {
        this.id_details = id_details;
        this.book = book;
        this.cart = cart;
        this.amount = amount;
    }

    public Integer getId_details() {
        return id_details;
    }

    public void setId_details(Integer id_details) {
        this.id_details = id_details;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
