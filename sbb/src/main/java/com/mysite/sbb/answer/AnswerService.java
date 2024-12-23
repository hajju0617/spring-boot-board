package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public void create(Question question, String content) {		// 답변 작성.
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setCreateDate(LocalDateTime.now());
		answer.setContent(content);
		this.answerRepository.save(answer);
	}
}
