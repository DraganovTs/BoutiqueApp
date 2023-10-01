package com.service.controller;


import com.homecode.commons.dto.ReviewDTO;
import com.service.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.homecode.commons.utils.Web.API;

@AllArgsConstructor
@RestController
@RequestMapping(API + "/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> findAll() {
        return this.reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ReviewDTO findById(@PathVariable("id") Long id) {
        return this.reviewService.findById(id);
    }

    @PostMapping("/create")
    public ReviewDTO create(@RequestBody ReviewDTO reviewDTO) {
        return this.reviewService.create(reviewDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.reviewService.delete(id);
    }
}
