package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.OrderRepository;
import com.nikonenko.springBookShop.services.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartDetailsService cartDetailsService;
    private final CartService cartService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public OrderController(OrderService orderService, CartDetailsService cartDetailsService, CartService cartService,
                           UserDetailsService userDetailsService) {
        this.orderService = orderService;
        this.cartDetailsService = cartDetailsService;
        this.cartService = cartService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/details")
    public String createOrderPage(@ModelAttribute("order") Order order){
        return "/order/create";
    }

    @PostMapping()
    public String createOrder(@ModelAttribute("order") Order order){
        User user = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).user();
        order.setUser(user);
        System.out.println("user setted for order");

        List<CartDetails> cartDetailsList = cartDetailsService.findByUser(user.getId_user());
        System.out.println("cartDetailsList found");
        List<Book> bookList = new ArrayList<>();
        for(CartDetails cartDetails : cartDetailsList){
            Book book = cartDetails.getBook();
            List<Order> orderList = book.getOrders();
            System.out.println("orderList got for Book in cycle");
            orderList.add(order);
            book.setOrders(orderList);
            System.out.println("orders setted for book");
            bookList.add(book);
        }
        order.setBooks(bookList);
        System.out.println("bookList setted for order");
        order.setStatus("Не доставлено");
        orderService.save(order);
        System.out.println("order saved");

        for(CartDetails cartDetails : cartDetailsList){
            int idBookOrder = orderService.getIdBookOrder(cartDetails.getBook().getId_book(), order.getId_order());
            orderService.setAmount(idBookOrder, cartDetails.getAmount());
        }

        cartService.delete(user.getId_user());
        System.out.println("cart deleted");

        return "redirect:/order/" + order.getId_order();
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Integer id, Model model){
        Order order = orderService.findOne(id).get();
        model.addAttribute("order", order);

        Map<Book, Integer> bookAmountMap = new HashMap<>();
        for(Book book : order.getBooks()){
            Integer idBookOrder = orderService.getIdBookOrder(book.getId_book(), order.getId_order());
            bookAmountMap.put(book, orderService.getBookAmount(idBookOrder));
        }
        model.addAttribute("bookAmountMap", bookAmountMap);

        model.addAttribute("orderPrice", bookAmountMap.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum());
        return "/order/show";
    }
}
