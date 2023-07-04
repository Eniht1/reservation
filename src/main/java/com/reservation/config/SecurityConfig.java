package com.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration //싱글톤으로 객체를 관리해준다.
@EnableWebSecurity //spring security filterChain이 자동으로 포함되게 한다.
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//람다로 변경됨
		
		//로그인에 대한 설정
			http.authorizeHttpRequests(authorize -> authorize
			//모든 사용자가 로그인 없이 접근할 수 있도록 설정
			.requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/fonts/**").permitAll()
			.requestMatchers("/", "/members/**", "/item/**").permitAll()
			.requestMatchers("/favicon.ico", "error").permitAll()
			.requestMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated())
		.formLogin(formLogin -> formLogin //2. 로그인 관련 설정
			.loginPage("/members/login") //로그인 페이지 URL
			.defaultSuccessUrl("/") //로그인 성공시 이동페이지
			.usernameParameter("email") //로그인시 id로 사용할 파라메터
			.failureUrl("/members/login/error")
			//.permitAll()
			) //로그인설정
		.logout(logout -> logout //3.로그아웃 관련 설정
			.logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
			.logoutSuccessUrl("/") // 로그아웃 성공시 이동할 url
			//.permitAll()
			) 
		//4. 인증되지않는 사용자가 리소스에 접근했을때 설정
		.exceptionHandling(handling -> handling
			.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
			)
		.rememberMe(Customizer.withDefaults());
			
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
