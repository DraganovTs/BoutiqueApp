package com.homecode.product.service;





import com.homecode.product.exception.CustomAlreadyExistException;
import com.homecode.product.exception.CustomDatabaseOperationException;
import com.homecode.product.exception.CustomNotFoundException;
import com.homecode.product.exception.CustomValidationException;
import com.homecode.product.model.dto.ReviewDTO;
import com.homecode.product.model.entity.Review;
import com.homecode.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ResponseEntity<List<ReviewDTO>> findAll() {
        log.debug("Request to get all Reviews");
        List<ReviewDTO> reviews = this.reviewRepository.findAll()
                .stream()
                .map(ReviewService::mapToDTO)
                .collect(Collectors.toList());
        if (reviews.isEmpty()) {
            throw new CustomNotFoundException("No reviews available.",
                    "REVIEWS_NOT_FOUND");
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    public ResponseEntity<ReviewDTO> findById(Long id) {
        log.debug("Request to get Review : {}", id);
        ReviewDTO reviewDTO = this.reviewRepository.findById(id).map(ReviewService::mapToDTO).orElse(null);
        if (reviewDTO == null) {
            throw new CustomNotFoundException("No reviews available.",
                    "REVIEWS_NOT_FOUND");
        }

        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    public ResponseEntity<ReviewDTO> create(ReviewDTO reviewDTO) {
        log.debug("Request to create Review : {}", reviewDTO);

        if (this.reviewRepository.findByTitle(reviewDTO.getTitle()).isPresent()) {
            throw new CustomAlreadyExistException("Review already exist",
                    "REVIEW_EXIST");
        }

        try {
            Review review = mapToReview(reviewDTO);

            this.reviewRepository.save(review);

            return new ResponseEntity<>(mapToDTO(review), HttpStatus.CREATED);
        } catch (Exception e) {

            throw new CustomDatabaseOperationException("An error occurred while deleting review with ID: "
                    , "DATABASE_OPERATION_EXCEPTION");
        }


    }


    public ResponseEntity<?> delete(Long id) {
        log.debug("Request to delete Review : {}", id);

        Optional<Review> reviewOptional = this.reviewRepository.findById(id);
        if (reviewOptional.isEmpty()){
            throw new CustomNotFoundException("No review whit id " + id,
                    "REVIEW_NOT_FOUND");
        }
        try {
            this.reviewRepository.deleteById(id);
            return new ResponseEntity<>("Delete review whit id " + id, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomDatabaseOperationException("An error occurred while deleting review with ID: " + id,
                    "DATABASE_OPERATION_EXCEPTION");
        }
    }

    public static ReviewDTO mapToDTO(Review review) {
        if (review == null) {
            throw new CustomValidationException("Not valid review",
                    "REVIEW_NOT_VALID");
        }
        return new ReviewDTO(
                review.getId(),
                review.getTitle(),
                review.getDescription(),
                review.getRating());
    }

    private Review mapToReview(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            throw new CustomValidationException("Not valid review",
                    "REVIEW_NOT_VALID");
        }
        return new Review(
                reviewDTO.getTitle(),
                reviewDTO.getDescription(),
                reviewDTO.getRating()
        );
    }
}
