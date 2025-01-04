package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content, SiteUser author) {		// 답변 작성.
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setCreateDate(LocalDateTime.now());
		answer.setContent(content);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
	}
}
