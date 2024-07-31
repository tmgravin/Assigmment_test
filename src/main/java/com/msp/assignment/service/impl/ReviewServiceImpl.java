package com.msp.assignment.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.msp.assignment.dto.ReviewDto;
import com.msp.assignment.model.Likes;
import com.msp.assignment.model.Review;
import com.msp.assignment.repository.LikesRepository;
import com.msp.assignment.repository.ReviewRepository;
import com.msp.assignment.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService
{
	@Autowired
	private ReviewRepository reviewRepo;
	
	@Autowired
	private LikesRepository likesRepo;
	
	@Override
	public Review saveReview(Review review)
	{
		if(review.getId() == null)
			return reviewRepo.save(review);
		else
			return review;
	}

	@Override
	public Long addUpdateLikes(Likes likes)
	{
		likes.setUpdatedAt(Timestamp.from(Instant.now()));
		return likesRepo.save(likes).getId();
	}
	
	@Override
	public List<ReviewDto> getReviewOfProject(Long projectId)
	{
		List<Review> reviewsList = reviewRepo.findByProjects(projectId);
		List<ReviewDto> dataSet = new ArrayList<ReviewDto>();
		ReviewDto dto = new ReviewDto();
		for(Review reviews: reviewsList)
		{
			reviews.setProjects(null);
			reviews.setUsers(null);
			dto.setProjectId(projectId);
			dto.setReview(reviews);
			dto.setLikesCount(likesRepo.countLikes(reviews.getId(), 'L'));
			dto.setDisLikesCount(likesRepo.countLikes(reviews.getId(), 'D'));
			dataSet.add(dto);
		}
		return dataSet;
	}
}
