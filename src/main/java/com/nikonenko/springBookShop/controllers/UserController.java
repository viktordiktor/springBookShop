package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Order;
import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.secutiry.UserDetails;
import com.nikonenko.springBookShop.services.BookService;
import com.nikonenko.springBookShop.services.OrderService;
import com.nikonenko.springBookShop.services.PersonService;
import com.nikonenko.springBookShop.services.UserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {
    public final OrderService orderService;
    public final UserDetailsService userDetailsService;
    public final PersonService personService;
    public final BookService bookService;

    public UserController(OrderService orderService, UserDetailsService userDetailsService, PersonService personService, BookService bookService){
        this.orderService = orderService;
        this.userDetailsService = userDetailsService;
        this.personService = personService;
        this.bookService = bookService;
    }
    @GetMapping("/showUserInfo")
    public String showUserInfo(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails.user());
        return "/users/show";
    }

    @GetMapping("/profile")
    public String profile(Model model){
        User user = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).user();
        model.addAttribute("user", user);

        Person person = user.getPerson();
        model.addAttribute("person", person);

        Map<Order, Integer> orderPrice = new HashMap<>();
        for(Order order : orderService.findAllByUser(user)){
            try {
            int sum = 0;
            for(Book book : order.getBooks()){
                Integer idBookOrder = orderService.getIdBookOrder(book.getId_book(), order.getId_order());
                Integer amount = orderService.getBookAmount(idBookOrder) != null
                        ? orderService.getBookAmount(idBookOrder)
                        : 0;
                sum += amount * book.getPrice();
            }
            orderPrice.put(order, sum);
            } catch(NullPointerException e){
                e.printStackTrace();
            }
        }
        model.addAttribute("orders", orderPrice);

        return "/users/show";
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User updatedUser){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userDetails.user();
        Person person = personService.findOne(id).get();
        updatedUser.setRole(user.getRole());
        updatedUser.setPerson(person);
        userDetailsService.update(id, updatedUser, person);

        return "redirect:/users/profile";
    }

    @GetMapping("/admin_panel")
    public String adminPanel(Model model){
        model.addAttribute("users", userDetailsService.findAll());
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        int sum = 0;
        for(Order order : orders){
            Set<Book> books = order.getBooks();
            for(Book book : books){
                sum += book.getPrice() * orderService.getBookAmount(
                        orderService.getIdBookOrder(book.getId_book(), order.getId_order()));
            }
        }
        model.addAttribute("sold", sum);
        return "/admin/dashboard";
    }

    @GetMapping("/admin_panel/orders")
    public String adminPanelOrders(Model model){
        Map<Order, Integer> orderPrice = new HashMap<>();
        for(Order order : orderService.findAll()){
            try {
                int sum = 0;
                for(Book book : order.getBooks()){
                    Integer idBookOrder = orderService.getIdBookOrder(book.getId_book(), order.getId_order());
                    Integer amount = orderService.getBookAmount(idBookOrder) != null
                            ? orderService.getBookAmount(idBookOrder)
                            : 0;
                    sum += amount * book.getPrice();
                }
                orderPrice.put(order, sum);
            } catch(NullPointerException e){
                e.printStackTrace();
            }
        }
        model.addAttribute("orders", orderPrice);
        return "/admin/orders";
    }

    @GetMapping("/admin_panel/books")
    public String adminPanelBooks(Model model){
        model.addAttribute("books", bookService.findAll());
        return "/admin/books";
    }
}