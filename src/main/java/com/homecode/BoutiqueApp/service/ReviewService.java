package com.homecode.BoutiqueApp.service;

import com.homecode.BoutiqueApp.model.Review;
import com.homecode.BoutiqueApp.model.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class ReviewService {

    public static ReviewDTO mapToDTO(Review review) {
        if (review != null){
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
