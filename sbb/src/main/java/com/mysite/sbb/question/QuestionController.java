package com.mysite.sbb.question;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

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
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page
					, @RequestParam(value = "kw", defaultValue = "") String kw) {			// 글 목록. (kw : 검색 시 사용할 검색어)
//		if (page < 1) {
//			throw new IllegalArgumentException("페이지는 1보다 커야됨.");
//		}
		Page<Question> paging = this.questionService.getList(page, kw);
		model.addAttribute("paging", paging);						// Page 객체를 'paging'이름으로 모델에 저장.
		model.addAttribute("keyWord", kw);
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
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {			// 수정 버튼 클릭 시 호출.
		Question question = this.questionService.getQuestion(id);
		if (!(question.getAuthor().getUsername().equals(principal.getName()))) {			// 로그인한 사용자와 글 작성자가 동일하지 않을 경우 예외처리.
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음.");
		}
		questionForm.setSubject(question.getSubject());										// 수정할 데이터를 보여주기위해서. 즉 기존에 작성되어 있던 데이터 담은 것.
		questionForm.setContent(question.getContent());
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String  questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult
								, Principal principal, @PathVariable("id") Integer id) {			// 글 수정 저장하기.
		if (bindingResult.hasErrors()) {
			return "question_form";
		}
		Question question = this.questionService.getQuestion(id);
		if (!(question.getAuthor().getUsername().equals(principal.getName()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음.");
		}
		this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
		return "redirect:/question/detail/" + id;			// return String.format(""redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Integer id) {			// 글 삭제.
		Question question = this.questionService.getQuestion(id);
		
		if (!(question.getAuthor().getUsername().equals(principal.getName()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없음.");
		}
		this.questionService.delete(question);
		return "redirect:/";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Integer id) {
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.questionService.vote(question, siteUser);
		return "redirect:/question/detail/" + id;
	}
}
