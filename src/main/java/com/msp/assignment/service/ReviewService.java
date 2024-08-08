package com.msp.assignment.service;

import java.util.List;

import com.msp.assignment.dto.ReviewDto;
import com.msp.assignment.model.Likes;
import com.msp.assignment.model.Review;

public interface ReviewService
{
	Review saveReview(Review review);
	Long addUpdateLikes(Likes likes);
	List<ReviewDto> getReviewOfProject(Long projectId);
}
