package com.aprec.webapp.user;

import java.time.LocalDateTime;
import java.util.Optional;

import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	@Query("UPDATE SiteUser u SET u.enabled = TRUE WHERE u.email = :email")
	int enableSiteUser(@Param("email") String email);

	@Transactional
    @Modifying(clearAutomatically = true)
	@Query("UPDATE SiteUser u SET u.password = :password WHERE u = :id")
	void updatePassword(@Param("password") String password, @Param("id") Long userId);

//	@Transactional
//    @Modifying(clearAutomatically = true)
//    @Query(value = """
//            UPDATE User u
//            SET u.deleteTime = ?1, u.deleteScheduled = 1 WHERE u = ?2
//            """, nativeQuery = true)
//    int scheduleDelete(LocalDateTime deleteTime, Long id);


//	@Transactional
//	@Modifying(clearAutomatically = true)
//	@Query("UPDATE User u SET u.deleteTime = :deleteTime, u.deleteScheduled = 1 WHERE u = :id")
//	int scheduleDelete(@Param("deleteTime") LocalDateTime deleteTime, @Param("id") Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE site_user
            SET delete_time = :deleteTime, delete_scheduled = true WHERE id = :id
            """, nativeQuery = true)
	void scheduleDelete(@Param("deleteTime") LocalDateTime deleteTime, @Param("id") Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = """
            UPDATE site_user
            SET delete_time = null, delete_scheduled = false WHERE id = :id
            """, nativeQuery = true)
	void cancelDelete(@Param("id") long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE SiteUser u SET u.username = :username WHERE u.id = :id")
	void updateUsername(@Param("username") String username, @Param("id") long id);

	@Query("SELECT u FROM SiteUser u WHERE u.id = ?1")
	Optional<SiteUser> findSiteUserById(Long id);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE SiteUser u SET u.email = :email WHERE u.id = :id")
	void updateEmail(@Param("email") String email, @Param("id") Long id);
}
