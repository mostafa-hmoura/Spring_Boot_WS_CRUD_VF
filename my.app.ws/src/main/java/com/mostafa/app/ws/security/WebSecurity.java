package com.mostafa.app.ws.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import com.mostafa.app.ws.services.UserService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .cors().and()
          .csrf().disable()
          .authorizeRequests()
          .antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
          .permitAll()
          .antMatchers("/v2/api-docs", "/configuration/ui", 
                  "/swagger-resources", "/configuration/security", 
                  "/swagger-ui.html", "/webjars/**", "/swagge‌​r-ui.html",
                  "/swagger-resources/configuration/ui", 
                  "/swagger-resources/configuration/security")
          .permitAll()
          .anyRequest().authenticated()
          .and()
          .addFilter(getAuthenticationFilter())
          .addFilter(new AuthorizationFilter(authenticationManager()))
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
	
	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl("/users/login");
		return filter;
	}
	
}
