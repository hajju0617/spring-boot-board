package com.mysite.sbb.answer;

import java.security.Principal;

import com.mysite.sbb.answer.dto.AnswerForm;
import com.mysite.sbb.answer.entity.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService;
	private final UserService userService;
	
	@PreAuthorize("isAuthenticated()")								// 로그인 된 사용자만 호출가능.
	@PostMapping("/create/{id}")
	public String createAnswer(Model model, @PathVariable("id") Integer id
							, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {		// 답변 작성.
		Question question = this.questionService.getQuestion(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		if (bindingResult.hasErrors()) {
			model.addAttribute("question", question);
			return "question_detail";
		}
		Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
//		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {		// 수정 버튼 누를 시 호출.
		Answer answer = this.answerService.getAnswer(id);
		
		if (!(answer.getAuthor().getUsername().equals(principal.getName()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음.");
		}
		answerForm.setContent(answer.getContent());
		return "answer_form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult
							, @PathVariable("id") Integer id, Principal principal) {								// 수정 저장하기.
		if (bindingResult.hasErrors()) {
			return "answer_form";
		}
		Answer answer = this.answerService.getAnswer(id);
		if (!(answer.getAuthor().getUsername().equals(principal.getName()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없음.");
		}
		this.answerService.modify(answer, answerForm.getContent());
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
//		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String answerDelete(Principal principal, @PathVariable("id") Integer id) {								// 답변 삭제.
		Answer answer = this.answerService.getAnswer(id);
		
		if (!(answer.getAuthor().getUsername().equals(principal.getName()))) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없음.");
		}
		this.answerService.delete(answer);
		return "redirect:/question/detail/" + answer.getQuestion().getId();
		// return String.format("redirect:/question/detail/%s", answer.getQuestion().getId();
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/vote/{id}")
	public String answerVote(Principal principal, @PathVariable("id") Integer id) {									// 답변 추천.
		Answer answer = this.answerService.getAnswer(id);
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.answerService.vote(answer, siteUser);
		return "redirect:/question/detail/" + answer.getQuestion().getId() + "#answer_" + answer.getId();
//		return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
	}
}
