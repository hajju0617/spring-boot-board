package com.mysite.sbb.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<SiteUser> optionalSiteUser = this.userRepository.findByUsername(username);
		
		if (optionalSiteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없음."); 
		}
		SiteUser siteUser = optionalSiteUser.get();
		List<GrantedAuthority> authorities = new ArrayList<>();					// GrantedAuthority : 사용자의 권한 정보를 나타내는 객체.
		if ("admin".equals(username)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		} else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
	}
}
