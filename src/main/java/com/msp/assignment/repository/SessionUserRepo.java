package com.msp.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.msp.assignment.model.SessionUsers;

@Repository
public interface SessionUserRepo extends JpaRepository<SessionUsers, Long>
{
	@Query(value = "select su.user_id, su.session_id, su.last_login, su.last_logout, su.remarks from session_users su"
			+ " left join users u on u.id = su.user_id where u.email = :email", nativeQuery=true)
	List<SessionUsers> getByUserEmail(@Param("email") String email);
	
	@Query(value = "select su.user_id, su.session_id, su.last_login, su.last_logout, su.remarks from session_users su"
			+ " where su.last_login is null", nativeQuery=true)
	List<SessionUsers> getLoggedInUsers();
	
	@Query(value = "select su.user_id, su.session_id, su.last_login, su.last_logout, su.remarks from session_users su"
			+ " where su.last_login is not null", nativeQuery=true)
	List<SessionUsers> getLoggedOutUsers();
}
