package com.aprec.tristan.user.registration.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aprec.tristan.user.User;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

	Optional<ConfirmationToken> findByToken(String token);
	Optional<ConfirmationToken> findByUser(User user);
	
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmationTime = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmationTime(String token, LocalDateTime confirmationTime);

	@Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.creationTime = ?2, c.expirationTime = ?3, c.token = ?4 " +
            "WHERE c.token = ?1")
    int updateToken(String token, 
    		LocalDateTime creationTime, 
    		LocalDateTime expirationTime, 
    		String newToken);
}
