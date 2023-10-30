package com.homecode.product.controller;



import com.homecode.product.model.dto.ReviewDTO;
import com.homecode.product.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homecode.product.utils.Web.API;


@AllArgsConstructor
@RestController
@RequestMapping(API + "/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/all")
    public ResponseEntity<List<ReviewDTO>> findAll() {
        return this.reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable("id") Long id) {
        return this.reviewService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> create(@RequestBody ReviewDTO reviewDTO) {
        return this.reviewService.create(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
      return this.reviewService.delete(id);
    }
}
