package com.msp.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msp.assignment.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>
{
	@Query(value="select id, rating, comment, created_at, users_id, project_id from reviews r "
			+ "where r.project_id = :projectId", nativeQuery = true)
	List<Review> findByProjects(@Param("projectId") Long projectId);
}