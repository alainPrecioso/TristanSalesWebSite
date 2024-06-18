package com.aprec.webapp.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.aprec.webapp.user.registration.token.ConfirmationTokenServiceInterface;
import com.aprec.webapp.user.token.PasswordTokenServiceInterface;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private PasswordTokenServiceInterface passwordTokenService;
	@Mock
	private ConfirmationTokenServiceInterface confirmationTokenService;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Mock
	private UserRepository userRepository;
	
	private UserService underTest;

	@BeforeEach
	void setUp() throws Exception {
		underTest = new UserService(userRepository, 
				bCryptPasswordEncoder, 
				confirmationTokenService,
				passwordTokenService
				);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoadUserByUsername() {
		//given
		given(userRepository
				.findByCredential(anyString()))
        .willReturn(Optional.of(new SiteUser()));
		//when
		underTest.loadUserByUsername("test");
		//then
		verify(userRepository).findByCredential("test");
	}

	@Test
	void testLoadUserByUsernameThrow() {
		//given
		given(userRepository
				.findByCredential(anyString()))
        .willReturn(Optional.empty());
		//when
		//then
		assertThatThrownBy(() -> underTest.loadUserByUsername("test"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("user " + "test" + " not found");
	}
	
	@Test
	void testSignUpUser() {
		 // given
        SiteUser user = new SiteUser();
        user.setUsername("username");
        // when
        underTest.signUpUser(user);

        // then
        ArgumentCaptor<SiteUser> userArgumentCaptor =
                ArgumentCaptor.forClass(SiteUser.class);
        ArgumentCaptor<SiteUser> tokenArgumentCaptor =
                ArgumentCaptor.forClass(SiteUser.class);
        
        verify(userRepository)
                .save(userArgumentCaptor.capture());
        
        verify(confirmationTokenService)
        .createConfirmationToken(tokenArgumentCaptor.capture());
        
        SiteUser capturedUser = userArgumentCaptor.getValue();
        SiteUser capturedUserToken = tokenArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(capturedUserToken).isEqualTo(user);
	}

	@Test
	void testRefreshConfirmationToken() {
		//given
		SiteUser user = new SiteUser();
		//when
		underTest.getNewToken(user);
		//then
		verify(confirmationTokenService).refreshConfirmationToken(user);
	}

	@Test
	void testEnableUser() {
		//given
		//when
		underTest.enableUser(null);
		//then
		verify(userRepository).enableSiteUser(null);
	}

	@Test
	void testGetUser() {
		//given
		given(userRepository
				.findByCredential(anyString()))
			.willReturn(java.util.Optional.of(new SiteUser()));
		//when
		underTest.getSiteUser("test");
		//then
		verify(userRepository).findByCredential("test");
		
	}

}
