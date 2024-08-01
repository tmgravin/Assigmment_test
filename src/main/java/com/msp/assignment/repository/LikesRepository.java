package com.msp.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msp.assignment.model.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long>
{
	@Query(value="select count(l.likes) from likes l where l.reviews_id = :reviewsId and l.likes = :type", nativeQuery = true)
	Integer countLikes(@Param("reviewsId") Long reviewsId ,@Param("type") char type);
}
