package com.aprec.tristan.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserSite, Long>{
	@Query("SELECT u FROM UserSite u WHERE u.username = ?1 OR u.email = ?1")
	Optional<UserSite> findByCredential(String credential);
	
	Optional<UserSite> findByEmail(String email);

	Optional<UserSite> findByUsername(String username);
	
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserSite u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

}
