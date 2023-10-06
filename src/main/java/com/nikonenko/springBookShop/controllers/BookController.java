package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Person;
import com.nikonenko.springBookShop.models.Review;
import com.nikonenko.springBookShop.services.BookService;
import com.nikonenko.springBookShop.utils.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookService.findAll());
        return "/books/index";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @PathVariable("id") Integer id, @ModelAttribute("review") Review review){
        if(bookService.findOne(id).isPresent()) {
            model.addAttribute("book", bookService.findOne(id).get());
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("currentUser", SecurityContextHolder.getContext().getAuthentication().getName());
            return "/books/show";
        }
        return "redirect:/books";
    }
}
