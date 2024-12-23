package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")	// URL 프리픽스
@Controller						// 컨트롤러 선언.
@RequiredArgsConstructor		// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어줌.
public class QuestionController {
	private final QuestionService questionService;
	
	@GetMapping("/list")
	public String list(Model model) {										// 글 목록.
		List<Question> questionList = this.questionService.getList();
		model.addAttribute("questionList", questionList);
		return "question_list";
	}
	
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {		// 상세 페이지.
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
}
