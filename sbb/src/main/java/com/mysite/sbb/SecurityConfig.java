package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration										// 해당 파일이 스프링의 환경 설정 파일임을 선언.
@EnableWebSecurity									// 모든 요청 URL이 시큐리티의 제어를 받도록 만듦.
@EnableMethodSecurity(prePostEnabled = true)		// @PreAuthorize에노테이션이 정상적으로 동작할 수 있도록
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(
				(authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()	// 로그인하지 않아도 모든 페이지에 접근 가능. (AntPathRequestMatcher는 특정 URL에 대한 요청을 매칭하는 데 사용.)
																																// (/** : 루트 경로 하위의 모든 경로에 매칭, /* : 루트 경로 바로 아래만의 경로만.)
				
				).csrf((csrf) -> csrf
						.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))					// h2-console로 시작하는 모든 URL은 CSRF 검증하지 않음.
		
				.headers((headers) -> headers
						.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))		// 프레임 구조로 되어 있는 H2 허락.
				
				.formLogin((formLogin) -> formLogin
						.loginPage("/user/login").defaultSuccessUrl("/"))		// .formLogin 메서드는 시큐리티의 로그인 설정을 담당.
				
				.logout((logout) -> logout										// logout() 메서드는 시큐리티의 로그아웃 설정을 담당.
						.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID"));
		return httpSecurity.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {				// PasswordEncoder 객체를 빈으로 등록.
		return new BCryptPasswordEncoder();			// PasswordEncoder 타입으로 된 BCryptPasswordEncoder 객체.
	}
	
	/* 
	 	AuthenticationManager 빈을 생성.
	 	AuthenticationManager은 시큐리티의 인증을 처리함.
	 	UserSecurityService, PasswordEncoder를 내부적으로 사용해서 인증과 권한 부여 프로세스를 처리함.
	*/
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
