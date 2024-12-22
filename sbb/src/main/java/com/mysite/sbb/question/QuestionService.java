package com.mysite.sbb.question;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	
	public List<Question> getList() {						// 글 목록.
		return this.questionRepository.findAll();
	}
	
	public Question getQuestion(Integer id) {
		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
}
