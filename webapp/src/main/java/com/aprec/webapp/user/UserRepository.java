package com.aprec.webapp.user;

import java.time.LocalDateTime;
import java.util.Optional;

import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aprec.webapp.user.entities.GitHubUser;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM SiteUser u WHERE u.username = ?1 OR u.email = ?1")
	Optional<SiteUser> findByCredential(String credential);
	
	Optional<SiteUser> findByEmail(String email);

	Optional<SiteUser> findSiteUserByUsername(String username);
	
	Optional<GitHubUser> findGitHubUserByUsername(String username);
	
	Optional<GitHubUser> findGitHubUserByIdentifier(int i);
	
	Streamable<User> findUserByDeleteScheduledTrue();
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE SiteUser u
            SET u.enabled = TRUE WHERE u.email = ?1
            """, nativeQuery = true)
    int enableSiteUser(String email);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE SiteUser u
            SET u.password = ?1 WHERE u = ?2
            """, nativeQuery = true)
    int updatePassword(String password, SiteUser user);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE User u
            SET u.deleteTime = ?1, u.deleteScheduled = 1 WHERE u = ?2
            """, nativeQuery = true)
    int scheduleDelete(LocalDateTime deleteTime, User username);
	
	@Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE User u
            SET u.deleteTime = NULL, u.deleteScheduled = 0 WHERE u = ?1
            """, nativeQuery = true)
	void cancelDelete(User loggedUser);

}
