package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.mysite.sbb.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.answer.entity.Answer;

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
	
	public Page<Question> getList(int page, String kw) {								// 글 목록 조회. (String kw : 검색 시 사용할 검색어)
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));							// Order by createDate DESC 역할.
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));		// page : 조회할 페이지 번호, 10 : 한 페이지에 보여줄 데이터의 개수.
//		return this.questionRepository.findAllByKeyword(kw, pageable);
		Specification<Question> specification = search(kw);
		return this.questionRepository.findAll(specification, pageable);
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
	
	public void vote(Question question, SiteUser siteUser) {				// 글 추천.
		question.getVoter().add(siteUser);
		this.questionRepository.save(question);
	}
	
	private Specification<Question> search(String kw) {						// Specification을 이용한 검색 메서드.
		return new Specification<>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
				query.distinct(true);  												// 조인3개에서 나오는 중복 제거. 
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(
                		  cb.like(q.get("subject"), "%" + kw + "%") 			// 글 제목 
                        , cb.like(q.get("content"), "%" + kw + "%")      		// 글 내용 
                        , cb.like(u1.get("username"), "%" + kw + "%")    		// 글 작성자 
                        , cb.like(a.get("content"), "%" + kw + "%")      		// 답변 내용 
                        , cb.like(u2.get("username"), "%" + kw + "%")			// 답변 작성자
                        ); 
			}
		};
	}
}
