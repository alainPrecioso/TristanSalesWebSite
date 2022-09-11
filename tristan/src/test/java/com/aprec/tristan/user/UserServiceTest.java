package com.aprec.tristan.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.aprec.tristan.user.registration.token.ConfirmationTokenService;
import com.aprec.tristan.user.token.PasswordTokenService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@Mock
	private PasswordTokenService passwordTokenService;
	@Mock
	private ConfirmationTokenService confirmationTokenService;
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
        .willReturn(java.util.Optional.of(new User()));
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
        .willReturn(java.util.Optional.empty());
		//when
		//then
		assertThatThrownBy(() -> underTest.loadUserByUsername("test"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("user " + "test" + " not found");
	}
	
	@Test
	void testSignUpUser() {
		 // given
        User user = new User();
        user.setUsername("username");
        // when
        underTest.signUpUser(user);

        // then
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<User> tokenArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        
        verify(userRepository)
                .save(userArgumentCaptor.capture());
        
        verify(confirmationTokenService)
        .createConfirmationToken(tokenArgumentCaptor.capture());
        
        User capturedUser = userArgumentCaptor.getValue();
        User capturedUserToken = tokenArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(capturedUserToken).isEqualTo(user);
	}

	@Test
	void testResetConfirmationToken() {
		//given
		User user = new User();
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
		verify(userRepository).enableUser(null);
	}

	@Test
	void testGetUser() {
		//given
		given(userRepository
				.findByUsername(anyString()))
			.willReturn(java.util.Optional.of(new User()));
		//when
		underTest.getUser("test");
		//then
		verify(userRepository).findByUsername("test");
		
	}

}
