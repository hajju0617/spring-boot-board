package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import com.mysite.sbb.answer.entity.Answer;
import org.springframework.stereotype.Service;

import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.user.entity.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	private final AnswerRepository answerRepository;
	
	public Answer create(Question question, String content, SiteUser author) {		// 답변 작성.
		Answer answer = new Answer();
		answer.setQuestion(question);
		answer.setCreateDate(LocalDateTime.now());
		answer.setContent(content);
		answer.setAuthor(author);
		this.answerRepository.save(answer);
		return answer;
	}
	
	public Answer getAnswer(Integer id) {											// 답변 조회 메서드.
		Optional<Answer> answer = this.answerRepository.findById(id);
		
		if (answer.isPresent()) {
			return answer.get();
		} else {
			throw new DataNotFoundException("answer not found");
		}
	}
	
	public void modify(Answer answer, String content) {								// 답변 내용 수정.
		answer.setContent(content);
		answer.setModifyDate(LocalDateTime.now());
		this.answerRepository.save(answer);
	}
	
	public void delete(Answer answer) {												// 답변 삭제.
		this.answerRepository.delete(answer);
	}
	
	public void vote(Answer answer, SiteUser siteUser) {							// 답변 추천.
		if (answer.getVoter().contains(siteUser)) {
			answer.getVoter().remove(siteUser);
		} else {
			answer.getVoter().add(siteUser);
		}
		this.answerRepository.save(answer);
	}
}
