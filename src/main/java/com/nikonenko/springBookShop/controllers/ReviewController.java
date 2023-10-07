package com.nikonenko.springBookShop.controllers;

import com.nikonenko.springBookShop.models.Book;
import com.nikonenko.springBookShop.models.Review;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.services.BookService;
import com.nikonenko.springBookShop.services.ReviewService;
import com.nikonenko.springBookShop.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final BookService bookService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public ReviewController(ReviewService reviewService, BookService bookService, UserDetailsService userDetailsService) {
        this.reviewService = reviewService;
        this.bookService = bookService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/{id}")
    public String createReview(@PathVariable Integer id, @ModelAttribute Review review){
        review.setUser(userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).user());
        review.setBook(bookService.findOne(id).get());
        reviewService.save(review);

        Book reviewedBook = bookService.findOne(id).get();
        List<Review> reviews = reviewedBook.getReviews();
        reviews.add(review);
        reviewedBook.setReviews(reviews);

        User user = userDetailsService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).user();
        List<Review> usersReviews = user.getReviews();
        usersReviews.add(review);
        user.setReviews(usersReviews);
        return "redirect:/books";
    }
}
