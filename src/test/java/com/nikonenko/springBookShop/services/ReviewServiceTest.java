package com.nikonenko.springBookShop.services;

import com.nikonenko.springBookShop.models.Review;
import com.nikonenko.springBookShop.models.User;
import com.nikonenko.springBookShop.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository mockReviewRepository;

    private ReviewService reviewServiceUnderTest;

    @BeforeEach
    void setUp() {
        reviewServiceUnderTest = new ReviewService(mockReviewRepository);
    }

    @Test
    void testFindAll() {
        // Setup
        // Configure ReviewRepository.findAll(...).
        final Review review = new Review();
        review.setId_review(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        review.setUser(user);
        final List<Review> reviews = List.of(review);
        when(mockReviewRepository.findAll()).thenReturn(reviews);

        // Run the test
        final List<Review> result = reviewServiceUnderTest.findAll();

        // Verify the results
    }

    @Test
    void testFindAll_ReviewRepositoryReturnsNoItems() {
        // Setup
        when(mockReviewRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Review> result = reviewServiceUnderTest.findAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testFindOne() {
        // Setup
        // Configure ReviewRepository.findById(...).
        final Review review1 = new Review();
        review1.setId_review(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        review1.setUser(user);
        final Optional<Review> review = Optional.of(review1);
        when(mockReviewRepository.findById(0)).thenReturn(review);

        // Run the test
        final Optional<Review> result = reviewServiceUnderTest.findOne(0);

        // Verify the results
    }

    @Test
    void testFindOne_ReviewRepositoryReturnsAbsent() {
        // Setup
        when(mockReviewRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Review> result = reviewServiceUnderTest.findOne(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        // Setup
        final Review review = new Review();
        review.setId_review(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        review.setUser(user);

        // Run the test
        reviewServiceUnderTest.save(review);

        // Verify the results
        verify(mockReviewRepository).save(any(Review.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Review updatedReview = new Review();
        updatedReview.setId_review(0);
        final User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole("role");
        updatedReview.setUser(user);

        // Run the test
        reviewServiceUnderTest.update(0, updatedReview);

        // Verify the results
        verify(mockReviewRepository).save(any(Review.class));
    }

    @Test
    void testDelete() {
        // Setup
        // Run the test
        reviewServiceUnderTest.delete(0);

        // Verify the results
        verify(mockReviewRepository).deleteById(0);
    }
}
