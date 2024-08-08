package com.msp.assignment.model;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "session_users")
@Data
public class SessionUsers
{
	@Id
    @Column(name = "user_id")
    private Long userId;
	
	@Column(name="session_id", length=50)
	private String sessionId;
	
	@Column(name = "last_login", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastLogin;
	
	@Column(name = "last_logout")
    private Timestamp lastLogout;
	
	@Column(name="remarks", length=50)
	private String remarks;
}
