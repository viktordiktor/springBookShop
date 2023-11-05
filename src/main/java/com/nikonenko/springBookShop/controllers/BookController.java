package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Review;
import com.nikonenko.springBookShop.services.BookService;
import com.nikonenko.springBookShop.utils.BookValidator;
import com.nikonenko.springBookShop.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            return "/books/show";
        }
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") Integer id){
        bookService.delete(id);
        return "redirect:/users/admin_panel/books";
    }

    @GetMapping("/edit/{id}")
    public String getEditBookPage(@PathVariable Integer id, Model model){
        model.addAttribute("book", bookService.findOne(id).get());
        return "/books/edit";
    }

    @GetMapping("/new")
    public String getNewBookPage(@ModelAttribute("book") Book book){
        return "/books/new";
    }

    @PatchMapping("/{id}")
    public String editBook(@ModelAttribute("book") Book book, BindingResult bindingResult,
                           @RequestParam("img") MultipartFile file, @PathVariable Integer id){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            //TODO: catch
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        book.setImage(fileName);
        bookService.update(id, book);
        String uploadDir = "src/main/resources/static/public/pictures/" + id;
        String uploadTarget = "target/classes/static/public/pictures/" + id;

        try{
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            FileUploadUtil.saveFile(uploadTarget, fileName, file);
        } catch(IOException e){
            //TODO: catch
        }

        bookService.update(id, book);
        return "redirect:/users/admin_panel/books";
    }

    @PostMapping("/new")
    public String addBook(@ModelAttribute("book") Book book, BindingResult bindingResult,
                           @RequestParam("img") MultipartFile file){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors()) {
            //TODO: catch
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        book.setImage(fileName);
        Book savedBook = bookService.save(book);
        String uploadDir = "src/main/resources/static/public/pictures/" + savedBook.getId_book();
        String uploadTarget = "target/classes/static/public/pictures/" + savedBook.getId_book();
        try{
            FileUploadUtil.saveFile(uploadDir, fileName, file);
            FileUploadUtil.saveFile(uploadTarget, fileName, file);
        } catch(IOException e){
            //TODO: catch
        }
        return "redirect:/users/admin_panel/books";
    }

    @GetMapping("/{category}/{text}")
    public String findBooks(Model model, @PathVariable("category") String category, @PathVariable("text") String text){
        List<Book> books;
        switch(category){
            case "title" -> {
                books = bookService.findByNameContaining(text);
            }
            case "author" -> {
                books = bookService.findByAuthorContaining(text);
            }
            case "genre" -> {
                books = bookService.findByGenreContaining(text);
            }
            default -> {
                return "redirect:/books";
            }
        }
        model.addAttribute("books", books);
        System.out.println(books.size());
        return "/books/find";
    }
}
