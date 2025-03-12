package com.mysite.sbb.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {
	@Size(min = 3, max = 25)
	@NotBlank(message = "사용자 ID는 필수 입력 항목.")
	private String username;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목.")
	private String password;
	
	@NotBlank(message = "비밀번호 확인은 필수 입력 항목.")
	private String confirmPassword;
	
	@Email
	@NotBlank(message = "이메일은 필수 입력 항목.")
	private String email;
}
