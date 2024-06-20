package com.aprec.webapp.user.token;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aprec.webapp.user.entities.User;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

	Optional<PasswordToken> findByToken(String token);
	Optional<PasswordToken> findByUser(User user);
	
	@Transactional
    @Modifying
    @Query(value = """
            UPDATE PasswordToken p
            SET p.confirmationTime = ?2
            WHERE p.token = ?1
            """, nativeQuery = true)
    int updateConfirmationTime(String token, LocalDateTime confirmationTime);

	@Transactional
    @Modifying
    @Query(value = """
            UPDATE PasswordToken p
            SET p.creationTime = ?2, p.expirationTime = ?3, p.token = ?4
            WHERE p.token = ?1
            """, nativeQuery = true)
    int updateToken(String token, 
    		LocalDateTime creationTime, 
    		LocalDateTime expirationTime, 
    		String newToken);
	
	@Transactional
	@Modifying
	@Query("DELETE PasswordToken p WHERE p.token = ?1")
	void deleteByToken(String token);
}
