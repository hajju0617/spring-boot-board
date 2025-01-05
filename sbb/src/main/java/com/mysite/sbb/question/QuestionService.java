package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionService {
	private final QuestionRepository questionRepository;
	
//	public List<Question> getList() {						// 글 목록.
//		return this.questionRepository.findAll();
//	}
	
	public Question getQuestion(Integer id) {							// 글 상세 조회.
		Optional<Question> question = this.questionRepository.findById(id);
		if (question.isPresent()) {
			questionRepository.updateCountView(id);
			return question.get();
		} else {
			throw new DataNotFoundException("question not found");
		}
	}
	
	public void create(String subject, String content, SiteUser user) {				// 글 작성.		
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setAuthor(user);
		question.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public Page<Question> getList(int page) {								// 글 목록 조회.
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));							// Order by createDate DESC 역할.
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));		// page : 조회할 페이지 번호, 10 : 한 페이지에 보여줄 데이터의 개수.
		return this.questionRepository.findAll(pageable);
	}
	
	public void modify(Question question, String subject, String content) {	// 글 수정.
		question.setSubject(subject);
		question.setContent(content);
		question.setModifyDate(LocalDateTime.now());
		this.questionRepository.save(question);
	}
	
	public void delete(Question question) {									// 글 삭제.
		this.questionRepository.delete(question);
	}
}
