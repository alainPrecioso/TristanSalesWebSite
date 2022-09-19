package com.aprec.tristan.user.oauth2;

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

import com.aprec.tristan.user.UserRepository;
import com.aprec.tristan.user.UserRole;

//@Service
public class GitHubUserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(GitHubUserService.class);

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User =  super.loadUser(userRequest);
//        log.info("Iteration:");
//        Iterator<Entry<String, Object>> it = oAuth2User.getAttributes().entrySet().iterator();   //line 1
//        while (it.hasNext()) {
//            Entry<String, Object> pair = it.next();
//            log.info(pair.getKey() + " = " + pair.getValue());
//            it.remove();
//           }
		RequestContextHolder.currentRequestAttributes().setAttribute("userType", "gitHub", SCOPE_SESSION);
        return processOAuth2User(oAuth2User);
    }

	private GitHubUser processOAuth2User(OAuth2User oAuth2User) {
		GitHubUser gitHubUser = new GitHubUser(oAuth2User);
		
		Optional<GitHubUser> userOptional = userRepository.findGitHubUserByUsername(gitHubUser.getName());
		if (userOptional.isPresent()) {
			
			userOptional.get().setOauth2User(oAuth2User);
			return userOptional.get();
			
		} else {
			gitHubUser.setUsername(gitHubUser.getName());
			
			Optional.ofNullable(gitHubUser.getEmailAttribute())
				.ifPresentOrElse(gitHubUser::setEmail, () -> gitHubUser.setEmail("undefined"));
			
			gitHubUser.setUserRole(UserRole.ROLE_USER);
			userRepository.save(gitHubUser);
			return gitHubUser;
		}
	}

}
