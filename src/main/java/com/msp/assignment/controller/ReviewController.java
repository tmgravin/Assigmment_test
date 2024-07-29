package com.msp.assignment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msp.assignment.model.Likes;
import com.msp.assignment.model.Review;
import com.msp.assignment.service.ReviewService;

@RestController
@RequestMapping("/api/review")
public class ReviewController
{
	@Autowired
	private ReviewService reviewService;
	
	private static final Logger log = LoggerFactory.getLogger(ReviewController.class);
	
	@PostMapping("/")
	public ResponseEntity<?> saveReview(@RequestBody Review review)
	{
		log.info("Inside saveReview method of ReviewController.");
		try
		{
			return ResponseEntity.ok().body(reviewService.saveReview(review));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getReview(@RequestParam("projectId") Long projectId)
	{
		log.info("Inside getReview method of ReviewController.");
		try
		{
			return ResponseEntity.ok().body(reviewService.getReviewOfProject(projectId));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}
	
	@PostMapping("/likes")
	public ResponseEntity<?> saveLikes(@RequestBody Likes likes)
	{
		log.info("Inside saveLikes method of ReviewController.");
		try
		{
			return ResponseEntity.ok().body(reviewService.addUpdateLikes(likes));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
		}
	}
}
