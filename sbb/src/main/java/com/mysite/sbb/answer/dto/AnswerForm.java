package com.mysite.sbb.answer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
	@NotBlank(message = "내용은 필수 항목.")
	private String content;
}
