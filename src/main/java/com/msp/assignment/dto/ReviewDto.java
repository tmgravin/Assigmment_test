package com.msp.assignment.dto;

import com.msp.assignment.model.Review;

import lombok.Data;

@Data
public class ReviewDto
{	
	private Review review;
	private Long projectId;
	private Integer likesCount;
	private Integer disLikesCount;
}
