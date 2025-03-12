package com.mysite.sbb.question.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
	@NotBlank(message = "제목은 필수 입력 항목.")		// NotEmpty (deprecated)
	@Size(max=200)
	private String subject;
	
	@NotBlank(message = "내용은 필수 입력 항목.")
	private String content;
}
