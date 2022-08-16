package com.aprec.tristan.security.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.aprec.tristan.user.auth.CustomAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	@Resource
	private final UserDetailsService userService;
	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

//	public WebSecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
//		super();
//		this.userService = userService;
//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//	}
	
	


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.csrf().disable()
            .authorizeRequests()
            //.antMatchers("/", "index", "/css/**", "/js/**", "/bootstrap/**").permitAll()
            //.antMatchers("userroletest", "/userroletest").hasRole(USER.name())
            //.anyRequest()
            //.permitAll()
            //.authenticated()
            .and()
            //.authenticationProvider(daoAuthenticationProvider())
            //.authenticationManager(authManager(http))
            .formLogin(form -> form
        			.loginPage("/login")
        			.failureHandler(authenticationFailureHandler())
        			//.failureUrl("/index?error")
        			.defaultSuccessUrl("/logged")
        			//.loginProcessingUrl("/index")
        			.permitAll())
            
            
            
            //.permitAll()
            
            //.antMatchers(HttpMethod.DELETE,"tbd").hasAnyAuthority(PLACEHOLCER_PERMISSION.name())
            ;
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
	
	@Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
	
	
	
}
