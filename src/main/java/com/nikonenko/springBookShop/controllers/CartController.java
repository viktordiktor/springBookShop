package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Cart;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.services.BookService;
import com.nikonenko.springBookShop.services.CartDetailsService;
import com.nikonenko.springBookShop.services.CartService;
import com.nikonenko.springBookShop.services.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartDetailsService cartDetailsService;
    private final BookService bookService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public CartController(CartService cartService, CartDetailsService cartDetailsService, BookService bookService, UserDetailsService userDetailsService) {
        this.cartService = cartService;
        this.cartDetailsService = cartDetailsService;
        this.bookService = bookService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/{id}")
    public String addToCart(@PathVariable Integer id){
        User user = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).user();
        if(cartService.findOne(user.getId_user()).isEmpty()){
            cartService.save(new Cart(user, null));
        }
        Cart cart = cartService.findOne(user.getId_user()).get();

        Book book = bookService.findOne(id).get();
        if(cartDetailsService.findByBookAndUser(book.getId_book(), user.getId_user()).isEmpty()){
            cartDetailsService.save(new CartDetails(book, cart, 1));
        } else{
            cartDetailsService.update
                    (cartDetailsService.findByBookAndUser(book.getId_book(), user.getId_user()).get().getId_details(),
                            new CartDetails(book, cart, cartDetailsService.
                    findByBookAndUser(book.getId_book(), user.getId_user()).get().getAmount() + 1));
        }
        CartDetails cartDetails = cartDetailsService.findByBookAndUser(book.getId_book(), user.getId_user()).get();

        List<CartDetails> booksCartDetails = book.getCartDetails();
        booksCartDetails.add(cartDetails);
        book.setCartDetails(booksCartDetails);

        List<CartDetails> usersCartDetails = cart.getCartDetails();
        if(usersCartDetails == null) usersCartDetails = new ArrayList<>();
        usersCartDetails.add(cartDetails);
        cart.setCartDetails(usersCartDetails);

        return "redirect:/cart";
    }

    @DeleteMapping("/{id}")
    public String deleteFromCart(@PathVariable Integer id){
       cartDetailsService.delete(id);
       return "redirect:/cart";
    }


    @GetMapping()
    public String showCart(Model model, @ModelAttribute Book book){
        model.addAttribute("user", userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().
                getAuthentication().getName()).user());
        Cart cart = cartService.findOne(userDetailsService.loadUserByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName()).user().getId_user()).get();
        List<CartDetails> cartDetailsList = cart.getCartDetails();
        model.addAttribute("cart", cartService.findOne(userDetailsService.loadUserByUsername
                (SecurityContextHolder.getContext().getAuthentication().getName()).user().getId_user()).get());
        model.addAttribute("cartDetails", cartDetailsList);
        return "cart/show";
    }

    @PostMapping("/{id}/{amount}")
    public String editAmount(@PathVariable Integer id, @PathVariable Integer amount){
        CartDetails cartDetails = cartDetailsService.findOne(id).get();
        cartDetails.setAmount(amount);
        cartDetailsService.update(id, cartDetails);
        return "redirect:/cart";
    }
}
