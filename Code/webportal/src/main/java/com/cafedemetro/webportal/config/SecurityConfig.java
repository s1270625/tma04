package com.cafedemetro.webportal.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

//1. Annotation for Configuration
@Configuration
//2. Annotation for Enabling Web Security
@EnableWebSecurity
public class SecurityConfig {

        //3. A String of query that select username and password from DB table
        private static final String USERQUERY = "select usercode, password, 1 from users where usercode = ?";
        //4. A String of query that select username and roles from DB table
        private static final String ROLEQUERY = "select usercode, rolename from roles where usercode = ?";
        //5. Dependency injection of Password Encoder
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth, DataSource dataSource) throws Exception {
			/*
				6. Configuration on auth object
					a. indicate that we use jdbc authentication
					b. set the password encoder by auto wired the Password Encoder, not pass by parameter
					c. set the datasource
					d. set the user name query
					e. set the authorities query 
			*/
			auth.jdbcAuthentication()
							.passwordEncoder(passwordEncoder)
							.dataSource(dataSource)
							.usersByUsernameQuery(USERQUERY)
							.authoritiesByUsernameQuery(ROLEQUERY);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			/*
				7. configure the security filter chain by using the http object
					a. set the logout page with default setting
					b. set the authorization request matcher
							i. No login required : login, logout, resources, h2-console, orders, takeOrder, customError
							ii. Roles staff, manager, superuser needed : /items, /items/**
							iii. Roles manager, superuser needed  : /itemCategories, /itemCategories/**
							iv. Roles superuser needed  : /branches, /branches/**
							v. Login required, no roles needed : all other paths
					c. set the login form with default setting
					d. set the concurrent session management
					e. set the remember me
					f. set the csrf
			*/
			http
				.logout(withDefaults())
				.authorizeHttpRequests(authorize -> authorize
					.requestMatchers("/login", "/logout", "/resources/**", "/h2-console",
									"/h2-console/**", "/orders/**", "/orders", "/",
									"/takeOrder", "/takeOrder/**",
									"/customError", "/customError/**")
					.permitAll()
					.requestMatchers("/items").hasAnyAuthority("staff", "manager", "superuser")
					.requestMatchers("/itemCategories").hasAnyAuthority("manager", "superuser")
					.requestMatchers("/branches").hasAnyAuthority("superuser")
					.anyRequest().authenticated())
				.formLogin(withDefaults())
				.sessionManagement(sessions -> sessions
								.sessionConcurrency(concurrency -> concurrency
												.maximumSessions(1)
												.expiredUrl("/login?expired")))
				.rememberMe(withDefaults())
				.csrf(csrf -> csrf.ignoringRequestMatchers("/login", "/logout", "/resources/**",
								"/h2-console",
								"/h2-console/**"));
			return http.build();
        }
}