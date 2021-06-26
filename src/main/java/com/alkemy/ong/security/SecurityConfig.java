package com.alkemy.ong.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.alkemy.ong.jwt.JwtEntryPoint;
import com.alkemy.ong.jwt.JwtFilter;
import com.alkemy.ong.service.impl.UsersServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsersServiceImpl userServiceImpl;
	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Bean
	public JwtFilter jwtTokenFilter() {
		return new JwtFilter();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/auth/me").hasAnyRole("ROLE_USER").and().authorizeRequests()
				.antMatchers(HttpMethod.DELETE, "/users/:id*").hasAnyRole("ROLE_USER").and().authorizeRequests()
				.antMatchers(HttpMethod.PATCH, "/users/:id").hasAnyRole("ROLE_USER").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/organization/public").hasAnyRole("ROLE_USER").and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/organization/public").hasAnyRole("ROLE_ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/users").hasAnyRole("ROLE_ADMIN").and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/categories", "/categories/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/categories").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/categories/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/categories/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/news/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/news").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/news/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/news/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/activities").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/activities/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/contacts").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/slides").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/slides/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/slides").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/slides/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/slides/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/contacts").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/backoffice/contacts").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/testimonials").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/testimonials/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/testimonials/:id").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/members").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/members").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/members/:id").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/members/:id").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/comments").hasAnyRole("ROLE_ADMIN").and()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/comments").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.PUT, "/comments/:id").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.DELETE, "/comments/:id").hasAnyRole("ROLE_USER").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/posts/:id/comments").hasAnyRole("ROLE_USER")
				.anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
