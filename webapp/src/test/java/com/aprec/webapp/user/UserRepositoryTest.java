/**
 *
 */
package com.aprec.webapp.user;

import com.aprec.webapp.user.entities.SiteUser;
import com.aprec.webapp.user.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author aprec
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
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail("username@domain.tld");
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findSiteUserByUsername(username);

        //then
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).usingRecursiveComparison().isEqualTo(user);
                });
    }

    @Test
    void shouldNotSelectUserByUsernameOnly() {
        //given
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername("username");
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findSiteUserByUsername(email);

        //then
        assertThat(optionalUser).isEmpty();
    }


    @Test
    void shouldSelectUserByEmailOnly() {
        //given
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername("username");
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findByEmail(email);

        //then
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).usingRecursiveComparison().isEqualTo(user);
                });
    }

    @Test
    void shouldNotSelectUserByEmailOnly() {
        //given
        String username = "username";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail("username@domain.tld");
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findByEmail(username);

        //then
        assertThat(optionalUser).isEmpty();
    }

    @Test
    void shouldSelectUserByEmail() {
        //given
        String username = "username";
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findByCredential(email);

        //then
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).usingRecursiveComparison().isEqualTo(user);
                });
    }


    @Test
    void shouldNotSelectUserByUsername() {
        //given
        String username = "username";
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findByCredential(username);

        //then
        assertThat(optionalUser)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).usingRecursiveComparison().isEqualTo(user);
                });
    }

    @Test
    void shouldSelectUserByAnyCredential() {
        //given
        String username = "username";
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUserWithUsername = underTest.findByCredential(username);
        Optional<SiteUser> optionalUserWithEmail = underTest.findByCredential(email);
        //then
        assertThat(optionalUserWithUsername)
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u)
                            .usingRecursiveComparison()
                            .isEqualTo(optionalUserWithEmail.get())
                    ;
                });
    }

    @Test
    void shouldNotSelectUserByAnyCredential() {
        //given
        String username = "username";
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        user.setEnabled(false);
        user.setLocked(false);
        underTest.save(user);
        //when
        Optional<SiteUser> optionalUser = underTest.findByCredential("fakenameington");
        //then
        assertThat(optionalUser).isEmpty();
    }

    @Test
    void shouldEnableUser() {
        //given
        String username = "username";
        String email = "username@domain.tld";
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password");
        user.setUserRole(UserRole.ROLE_USER);
        //user.setEnabled(false);
        //user.setLocked(false);
        underTest.save(user);
        //when
        underTest.enableSiteUser(email);
        Optional<SiteUser> optionalUser = underTest.findSiteUserByUsername(username);
        //then
        assertThat(optionalUser.get().isEnabled()).isTrue();
    }

}
