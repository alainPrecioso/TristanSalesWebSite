/**
 * 
 */
package com.aprec.tristan.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * @author aprec
 *
 */
@DataJpaTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository underTest;

	@AfterEach
	void tearDown() {
		underTest.deleteAll();
	}
	
	@Test
	void shouldSelectUserByUsernameOnly() {
		//given
		String username = "username";
		UserSite user = new UserSite();
		user.setUsername(username);
		user.setEmail("username@domain.tld");;
		user.setPassword("password");
		user.setUserRole(UserRole.ROLE_USER);
		user.setEnabled(false);
		user.setLocked(false);
		underTest.save(user);
		//when
		Optional<UserSite> optionalUser = underTest.findByUsername(username);
		
		//then
		assertThat(optionalUser)
        .isPresent()
        .hasValueSatisfying(u -> {
            assertThat(u).usingRecursiveComparison().isEqualTo(user);
        });
	}
	
	@Test
	void shouldEnableUser() {
		//given
		String username = "username";
		String email = "username@domain.tld";
		UserSite user = new UserSite();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword("password");
		user.setUserRole(UserRole.ROLE_USER);
		user.setEnabled(false);
		user.setLocked(false);
		underTest.save(user);
		//when
		underTest.enableUser(email);
		Optional<UserSite> optionalUser = underTest.findByUsername(username);
		//then
		assertThat(optionalUser.get().isEnabled()).isTrue();
	}

}
