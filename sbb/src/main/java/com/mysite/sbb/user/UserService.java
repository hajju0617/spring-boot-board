package com.mysite.sbb.user;

import java.util.Optional;

import com.mysite.sbb.user.entity.SiteUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.exception.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;			// 빈으로 등록한 PasswordEncoder 객체를 주입받아 사용.
	
	public SiteUser create(String username, String password, String email) {		// 회원가입.
		String hashPassword = passwordEncoder.encode(password);
		SiteUser user = SiteUser.dtoToEntity(username, hashPassword, email);
		this.userRepository.save(user);
		return user;
	}
	
	public SiteUser getUser(String userName) {			// 사용자 이름으로 SiteUser 객체 조회.
		Optional<SiteUser> siteUser = this.userRepository.findByUsername(userName);
		
		if (siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteUser not found");
		}
	}
}
