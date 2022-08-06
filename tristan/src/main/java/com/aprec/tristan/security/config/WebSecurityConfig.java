package com.aprec.tristan.security.config;

import static com.aprec.tristan.users.UserPermission.PLACEHOLCER_PERMISSION;
import static com.aprec.tristan.users.UserRole.ADMIN;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

//	public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		super();
//		this.userService = userService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
	
	


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http //.csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "index", "/css/**", "/js/**", "/bootstrap/**").permitAll()
            .antMatchers("tbd").hasRole(ADMIN.name())
            .antMatchers(HttpMethod.DELETE,"tbd").hasAnyAuthority(PLACEHOLCER_PERMISSION.name());
        //http.headers().frameOptions().sameOrigin();
        return http.build();
    }
	
    public WebSecurityConfig(UserDetailsService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
    	return auth.getAuthenticationManager();
    }
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);
		return provider;
	}
	
}
