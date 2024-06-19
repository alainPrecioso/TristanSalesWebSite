package com.aprec.webapp.user.oauth2;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.context.request.RequestContextHolder;

import com.aprec.webapp.user.UserRepository;

//@Service
public class GitHubUserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(GitHubUserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User =  super.loadUser(userRequest);
		RequestContextHolder.currentRequestAttributes().setAttribute("userType", "gitHub", SCOPE_SESSION);
        return processOAuth2User(oAuth2User);
    }

	private GitHubUser processOAuth2User(OAuth2User oAuth2User) {
		GitHubUser gitHubUser = new GitHubUser(oAuth2User);
		oAuth2User.getAttributes().forEach((s, o) -> System.out.println(s + " : " + o));
		Optional<GitHubUser> userOptional = userRepository.findGitHubUserByIdentifier(gitHubUser.getIdentifier());
		if (userOptional.isPresent()) {
			
			userOptional.get().setOauth2User(oAuth2User);
			return userOptional.get();
			
		} else {
			
			userRepository.save(gitHubUser);
			return gitHubUser;
		}
	}

}
