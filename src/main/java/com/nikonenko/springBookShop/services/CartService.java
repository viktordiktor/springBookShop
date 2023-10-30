package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findAll(){
        return cartRepository.findAll();
    }

    public Optional<Cart> findOne(int id){
        return cartRepository.findById(id);
    }

    @Transactional
    public void save(Cart cart){
        cartRepository.save(cart);
    }

    @Transactional
    public void update(int id, Cart updatedCart){
        updatedCart.setId_user(id);
        cartRepository.save(updatedCart);
    }

    @Transactional
    public void delete(int id){
        cartRepository.deleteById(id);
    }
}
