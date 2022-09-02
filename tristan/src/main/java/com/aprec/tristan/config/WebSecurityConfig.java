package com.aprec.tristan.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.aprec.tristan.user.auth.CustomAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Resource
	private final UserDetailsService userService;
	
	
	public WebSecurityConfig(UserDetailsService userService) {
		super();
		this.userService = userService;
	}

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.csrf().disable()
            .authorizeRequests()
            .and().formLogin()
        		.loginPage("/login")
        		.failureHandler(authenticationFailureHandler())
        		.defaultSuccessUrl("/logged")
        		.permitAll()
        		.and()
        		.rememberMe()
        	.and().logout()
        		.logoutUrl("/logout")
        		.clearAuthentication(true)
        		.deleteCookies("JSESSIONID", "remember-me")
        		.invalidateHttpSession(true)
        		.logoutSuccessUrl("/index")
            ;
        return http.build();
    }
	

    
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
    	return auth.getAuthenticationManager();
    }
    
	
	
	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider(BCryptPasswordEncoder bCryptPasswordEncoder) {
		DaoAuthenticationProvider provider =
				new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);
		return provider;
	}
	
	@Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
	@Bean
    SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
	
	
	
}
