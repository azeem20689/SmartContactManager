package com.smartcontact.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class MyConfig {

	@Bean
	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withUsername("azeem").password(encoder.encode("azeem#123")).roles("ADMIN").build();
//		UserDetails user = User.withUsername("user").password(encoder.encode("user#123")).roles("USER", "ADMIN")
//				.build();
//		return new InMemoryUserDetailsManager(admin, user);
		return new UserDetailsServiceImpl();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.csrf(c -> c.disable())
//				.authorizeHttpRequests().requestMatchers("/test/**","/").permitAll()
//				.requestMatchers("/user/**").authenticated()
//				.anyRequest().permitAll()
//				.and()
//				.formLogin()
//				.and().build();
		return http.csrf(c -> c.disable())
				.authorizeHttpRequests().requestMatchers("/user/sendEmail").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/user/**").authenticated()
				.anyRequest().permitAll()
				.and()
				.formLogin().loginPage("/logIn")
				.defaultSuccessUrl("/user/user-dashboard")
				.and()
				.build();
				
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
		dap.setUserDetailsService(userDetailsService());
		dap.setPasswordEncoder(passwordEncoder());
		return dap;
	}
}
