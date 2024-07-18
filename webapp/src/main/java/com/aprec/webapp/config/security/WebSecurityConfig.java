package com.aprec.webapp.config.security;

import com.aprec.webapp.user.auth.CustomAuthenticationFailureHandler;
import com.aprec.webapp.user.auth.oauth2.GitHubUserService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableAsync
@EnableTransactionManagement
@EnableScheduling
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Resource
    private final UserDetailsService userDetailsService;

    public WebSecurityConfig(UserDetailsService userDetailsService) {
        super();
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize ->
                        authorize
                                .anyRequest()
                                .permitAll()
                )
                .httpBasic(withDefaults())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .failureHandler(this.authenticationFailureHandler())
                                .defaultSuccessUrl("/index")
                                .permitAll())
                .logout(form ->
                        form
                                .logoutUrl("/logout")
                                .clearAuthentication(true)
                                .deleteCookies("JSESSIONID", "remember-me")
                                .invalidateHttpSession(true)
                                .logoutSuccessUrl("/index")
                )
                .rememberMe(withDefaults()).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .oauth2Login(login -> login
                        .loginPage("/oauth_login")
                        .defaultSuccessUrl("/index")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService())))
        ;
        return http.build();
    }


    @Bean
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new GitHubUserService();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }

    // commented out to use the default auth provider
//	@Bean
//	DaoAuthenticationProvider daoAuthenticationProvider(BCryptPasswordEncoder bCryptPasswordEncoder) {
//		DaoAuthenticationProvider provider =
//				new DaoAuthenticationProvider();
//		provider.setPasswordEncoder(bCryptPasswordEncoder);
//		provider.setUserDetailsService(userDetailsService);
//		return provider;
//	}


    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

}
