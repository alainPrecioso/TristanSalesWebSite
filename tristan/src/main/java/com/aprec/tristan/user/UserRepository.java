package com.aprec.tristan.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?1")
	Optional<User> findByCredential(String credential);
	
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
	
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u.password = ?1 WHERE u.id = ?2")
    int updatePassword(String password, Long id);

}
