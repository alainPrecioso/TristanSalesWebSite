package com.aprec.tristan.user.oauth2;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.aprec.tristan.user.User;
import com.aprec.tristan.user.UserRepository;

@Service
public class GitHubUserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;
	
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
        return processOAuth2User(oAuth2User);
    }

	private GitHubUser processOAuth2User(OAuth2User oAuth2User) {
		GitHubUser gitHubUser = new GitHubUser(oAuth2User);
		// see what other data from userRequest or oidcUser you need

//		Optional<User> userOptional = userRepository.findByUsername(gitHubUser.getUsername());
//		if (!userOptional.isPresent()) {
//			User user = new User();
//			if (gitHubUser.getEmail() != null) {
//				user.setEmail(gitHubUser.getEmail());
//			}
//			user.setUsername(gitHubUser.getUsername());
//
//			// set other needed data
//
//			userRepository.save(user);
//		}

		return gitHubUser;
	}

}
