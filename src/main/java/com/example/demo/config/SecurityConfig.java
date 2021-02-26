package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.service.UserSecurityDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserSecurityDetailsService userDetailsService;
	
	@Autowired
	public SecurityConfig(UserSecurityDetailsService userDetailsService) {
		super();
		this.userDetailsService = userDetailsService;
	}


//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		UserDetails user1 = User.builder().username("anatolii").password(passwordEncoder().encode("123")).roles("USER")
//				.build();
//
//		UserDetails user2 = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
//				.build();
//
//		auth.inMemoryAuthentication().withUser(user1);
//		auth.inMemoryAuthentication().withUser(user2);
//	}

	// Метод с сайта spring
//	@Override
//	protected void configure(HttpSecurity httpSecurity) throws Exception {
//		httpSecurity.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests()
//				.antMatchers("/console/**").permitAll();
//		httpSecurity.csrf().disable();
//		httpSecurity.headers().frameOptions().disable();
//	}



	//Метод с урока
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/notes/**").authenticated()
		.antMatchers("/folders/**").authenticated()
		.antMatchers("/users/**").hasRole("ADMIN")
		.and().formLogin().loginPage("/login")
		.and().logout().logoutSuccessUrl("/")
		.and().exceptionHandling().accessDeniedPage("/login?denied=true")
		.and().rememberMe().tokenValiditySeconds(2592000).userDetailsService(userDetailsService)
		.and().csrf().disable()
		;
	
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("h2-console/**");
	}
	
	
	//AuthenticationManager-управляет аутентификацией
	//AutheticationProvider-предоставляет данные для аутент
	//UserDetails-описывает одного пользователя поняного для Security
	//UserDetailsService-знает как получить UserDetails (пользователей для Security)
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//Предоставляет инфо для аутентификации
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
        auth.authenticationProvider(authenticationProvider);
   
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
