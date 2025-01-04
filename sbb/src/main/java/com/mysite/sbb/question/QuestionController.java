package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/question")	// URL 프리픽스
@Controller						// 컨트롤러 선언.
@RequiredArgsConstructor		// final이 붙은 속성을 포함하는 생성자를 자동으로 만들어줌.
public class QuestionController {
	private final QuestionService questionService;
	private final UserService userService;
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {	// 글 목록.
//		if (page < 1) {
//			throw new IllegalArgumentException("페이지는 1보다 커야됨.");
//		}
		Page<Question> paging = this.questionService.getList(page);
		model.addAttribute("paging", paging);						// Page 객체를 'paging'이름으로 모델에 저장.
		return "question_list";
	}
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {		// 상세 페이지.
		Question question = this.questionService.getQuestion(id);
		model.addAttribute("question", question);
		return "question_detail";
	}
	
	@PreAuthorize("isAuthenticated()")								// 로그인 된 사용자만 호출가능.
	@GetMapping("/create")	
	public String questionCreate(QuestionForm questionForm) {		// 글 등록(작성) 버튼 클릭 시 호출.
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")								// 로그인 된 사용자만 호출가능.
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {		// 글 저장.
		if (bindingResult.hasErrors()) {
			return "/question_form";
		}
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
		return "redirect:/question/list";
	}
}
