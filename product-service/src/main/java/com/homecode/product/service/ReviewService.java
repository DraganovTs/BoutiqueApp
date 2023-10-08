package com.homecode.product.service;

import com.homecode.commons.dto.ReviewDTO;
import com.homecode.product.exception.CustomAlreadyExistException;
import com.homecode.product.exception.CustomDatabaseOperationException;
import com.homecode.product.exception.CustomNotFoundException;
import com.homecode.product.exception.CustomValidationException;
import com.homecode.product.model.Review;
import com.homecode.product.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

            return new ResponseEntity<>(reviewDTO, HttpStatus.CREATED);
        } catch (Exception e) {

            throw new CustomDatabaseOperationException(e.getMessage(), "DATABASE_OPERATION_EXCEPTION");
        }


    }


    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        try {
        this.reviewRepository.deleteById(id);
        }catch (Exception e){
            throw new CustomNotFoundException("No review whit id " + id,
                    "REVIEW_NOT_FOUND");
        }
    }

    public static ReviewDTO mapToDTO(Review review) {
        if (review == null) {
            throw  new CustomValidationException("Not valid review",
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
