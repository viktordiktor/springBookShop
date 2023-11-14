package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.CartDetails;
import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final CartDetailsService cartDetailsService;
    private final CartService cartService;
    private final UserService userService;

    @Autowired
    public OrderController(OrderService orderService, CartDetailsService cartDetailsService, CartService cartService,
                           UserService userService) {
        this.orderService = orderService;
        this.cartDetailsService = cartDetailsService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/details")
    public String createOrderPage(@ModelAttribute("order") Order order){
        return "/order/create";
    }

    @PostMapping()
    public String createOrder(@ModelAttribute("order") Order order){
        User user = userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication()
                .getName()).user();
        order.setUser(user);

        List<CartDetails> cartDetailsList = cartDetailsService.findByUser(user.getId_user());
        Set<Book> bookList = new HashSet<>();
        for(CartDetails cartDetails : cartDetailsList){
            Book book = cartDetails.getBook();
            Set<Order> orderList = book.getOrders();
            orderList.add(order);
            book.setOrders(orderList);
            bookList.add(book);
        }
        order.setBooks(bookList);
        order.setStatus("Не доставлено");
        orderService.save(order);

        for(CartDetails cartDetails : cartDetailsList){
            int idBookOrder = orderService.getIdBookOrder(cartDetails.getBook().getId_book(), order.getId_order());
            orderService.setAmount(idBookOrder, cartDetails.getAmount());
        }

        cartService.delete(user.getId_user());

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

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Integer id){
        orderService.delete(id);
        return "redirect:/users/admin_panel/orders";
    }

    @PatchMapping("/deliver/{id}")
    public String deliverOrder(@PathVariable Integer id, RedirectAttributes redirectAtt){
        Order order = orderService.findOne(id).isPresent() ? orderService.findOne(id).get() : new Order();
        order.setStatus("Доставлено");
        orderService.update(id, order);
        redirectAtt.addAttribute("id", id);
        return "redirect:/order/{id}";
    }
}
