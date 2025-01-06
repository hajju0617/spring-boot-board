package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
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
}
