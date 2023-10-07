package com.homecode.product.service;

import com.homecode.commons.dto.ReviewDTO;
import com.homecode.product.exception.CustomReviewException;
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
            throw new CustomReviewException("No reviews available.",
                    "REVIEWS_NOT_FOUND");
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    public ReviewDTO findById(Long id) {
        log.debug("Request to get Review : {}", id);
        return this.reviewRepository.findById(id).map(ReviewService::mapToDTO).orElse(null);
    }

    public ReviewDTO create(ReviewDTO reviewDTO) {
        log.debug("Request to create Review : {}", reviewDTO);
        return mapToDTO(this.reviewRepository.save(
                new Review(
                        reviewDTO.getTitle(),
                        reviewDTO.getDescription(),
                        reviewDTO.getRating()
                )
        ));
    }

    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        this.reviewRepository.deleteById(id);
    }

    public static ReviewDTO mapToDTO(Review review) {
        if (review != null) {
            return new ReviewDTO(
                    review.getId(),
                    review.getTitle(),
                    review.getDescription(),
                    review.getRating()
            );
        }
        return null;
    }
}
