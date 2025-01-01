package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration					// 해당 파일이 스프링의 환경 설정 파일임을 선언.
@EnableWebSecurity				// 모든 요청 URL이 시큐리티의 제어를 받도록 만듦.
public class SecurityConfig {
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.authorizeHttpRequests(
				(authorizeHttpRequests) -> authorizeHttpRequests.requestMatchers(new AntPathRequestMatcher("/**")).permitAll()	// 로그인하지 않아도 모든 페이지에 접근 가능.
																																// (/** : 루트 경로 하위의 모든 경로에 매칭, /* : 루트 경로 바로 아래만의 경로만.)
				
				).csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))					// h2-console로 시작하는 모든 URL은 CSRF 검증하지 않음.
				.headers((headers) -> headers.addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
				);
		return httpSecurity.build();
	}
}
