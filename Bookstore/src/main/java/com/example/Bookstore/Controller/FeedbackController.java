package com.example.Bookstore.Controller;

import com.example.Bookstore.Model.Feedback;
import com.example.Bookstore.DTO.FeedbackDTO;
import com.example.Bookstore.Service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookstore_user")
@Validated
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    
    @GetMapping("/get/feedback/{productId}")
    public List<Feedback>getFeedbackByProductId(@PathVariable("productId") Long productId){
        return feedbackService.getFeedbackByProductId(productId);
    }

    @PostMapping("/add/feedback/{product_id}")
    public String addFeedback(@PathVariable Long product_id, @Valid @RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.addFeedback(product_id,feedbackDTO);
    }


}
