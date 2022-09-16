package com.aprec.tristan.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aprec.tristan.user.oauth2.GitHubUser;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM SiteUser u WHERE u.username = ?1 OR u.email = ?1")
	Optional<SiteUser> findByCredential(String credential);
	
	Optional<SiteUser> findByEmail(String email);

	@Query("SELECT u FROM SiteUser u WHERE u.username = ?1")
	Optional<SiteUser> findSiteUserByUsername(String username);
	
	@Query("SELECT u FROM GitHubUser u WHERE u.username = ?1")
	Optional<GitHubUser> findGitHubUserByUsername(String username);
	
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
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u.deleteTime = ?1, u.deleteScheduled = 1 WHERE u = ?2")
    int scheduleDelete(LocalDateTime deleteTime, User username);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u " +
            "SET u.deleteTime = null, u.deleteScheduled = 1 WHERE u.username = ?1")
    int unScheduleDelete(String username);
	
	@Query("SELECT u FROM User u WHERE u.deleteScheduled = 1")
	List<User> findUsersScheduledForDelete();
}
