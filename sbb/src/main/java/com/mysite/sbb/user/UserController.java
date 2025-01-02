package com.mysite.sbb.user;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {				// 회원가입을 위한 페이지
		return "signup_form";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {			// 회원가입
		if (bindingResult.hasErrors()) {																		// @Valid 검증 실패 시.
			return "signup_form";
		}
		if (!(userCreateForm.getPassword().equals(userCreateForm.getConfirmPassword()))) {						// 비밀번호 검증 실패.
			bindingResult.rejectValue("confirmPassword", "passwordIncorrect", "2개의 비밀번호가 일치하지 않음.");		
			return "signup_form";
		}
		
		try {
			userService.create(userCreateForm.getUsername(), userCreateForm.getPassword(), userCreateForm.getEmail());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록되어 있는 사용자.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		return "redirect:/";
	}
}
