package com.nikonenko.springBookShop.models;

import jakarta.persistence.*;
import org.springframework.security.core.Transient;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "id_user")
    private Integer id_user;

    @OneToOne(optional = true)
    @MapsId
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartDetails> cartDetails;

    public Cart(){}

    public Cart(User user, List<CartDetails> cartDetails) {
        this.user = user;
        this.cartDetails = cartDetails;
    }

    public Cart(Integer id_user, User user, List<CartDetails> cartDetails) {
        this.id_user = id_user;
        this.user = user;
        this.cartDetails = cartDetails;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartDetails> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetails> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public Integer cartPrice(){
        return this.cartDetails.stream()
                .mapToInt(test -> test.getAmount() * test.getBook().getPrice())
                .sum();
    }

    public Integer cartAmount(){
        return this.cartDetails.stream().mapToInt(CartDetails::getAmount).sum();
    }
}
