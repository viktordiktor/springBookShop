package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.repositories.CartDetailsRepository;
import com.nikonenko.springBookShop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartDetailsService {
    private final CartDetailsRepository cartDetailsRepository;

    @Autowired
    public CartDetailsService(CartDetailsRepository cartDetailsRepository) {
        this.cartDetailsRepository = cartDetailsRepository;
    }

    public List<CartDetails> findAll(){
        return cartDetailsRepository.findAll();
    }

    public Optional<CartDetails> findOne(int id){
        return cartDetailsRepository.findById(id);
    }

    @Transactional
    public void save(CartDetails cart){
        cartDetailsRepository.save(cart);
    }

    @Transactional
    public void update(int id, CartDetails updatedCart){
        updatedCart.setId_details(id);
        cartDetailsRepository.save(updatedCart);
    }

    public Optional<CartDetails> findByBookAndUser(Integer idBook, Integer idUser){
        return cartDetailsRepository.findByBookAndUser(idBook, idUser);
    }

    @Transactional
    public void delete(int id){
        cartDetailsRepository.deleteById(id);
    }
}
