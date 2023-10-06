package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Review;
import com.nikonenko.springBookShop.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> findAll(){
        return reviewRepository.findAll();
    }

    public Optional<Review> findOne(int id){
        return reviewRepository.findById(id);
    }

    @Transactional
    public void save(Review review){
        reviewRepository.save(review);
    }

    @Transactional
    public void update(int id, Review updatedReview){
        updatedReview.setId_review(id);
        reviewRepository.save(updatedReview);
    }

    @Transactional
    public void delete(int id){
        reviewRepository.deleteById(id);
    }

}
