package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.question.QuestionService;


@SpringBootTest
class SbbApplicationTests {
	@Autowired	// 의존성 주입(DI)
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private QuestionService questionService;
	
	// 더미데이터
	@Test
	void testData() {
		for (int i = 1; i <= 100; i++) {
			String subject = String.format("테스트 더미데이터 제목 : [%03d]", i);
			String content = "데스트 더미데이터 내용.";
			this.questionService.create(subject, content, null);
		}
	}
	
	
	// Question 데이터 조회
//	@Test
//	void testJpa() {
//		Question q1 = new Question();
//		q1.setSubject("sbb는 spring boot board.");
//		q1.setContent("sbb 공부를 열심히 하자.");
//		q1.setCreateDate(LocalDateTime.now());
//		// 위 코드에서 작성한 데이터를 .save() 를 통해 question 테이블에 저장. (1번째 행)
//		this.questionRepository.save(q1);
//		
//		Question q2 = new Question();
//		q2.setSubject("스프링 부트는 신기해.");
//		q2.setContent("백엔드 공부 열심히 하자.");
//		q2.setCreateDate(LocalDateTime.now());
//		// 위 코드에서 작성한 데이터를 .save() 를 통해 question 테이블에 저장. (2번째 행)
//		this.questionRepository.save(q2);
//	}
	
//	@Test
//	void testJpa2() {
//		List<Question> all = this.questionRepository.findAll(); 
//		assertEquals(2, all.size());
//		
//		Question question = all.get(0);
//		assertEquals("sbb는 spring boot board.", question.getSubject());
//	}
	
//	@Test
//	void Jpa3() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		if (oq.isPresent()) {
//			Question q = oq.get();
//			assertEquals("sbb는 spring boot board.", q.getSubject());
//		} 
//	}
	
//	@Test
//	void Jpa4() {
//		Question q = this.questionRepository.findBySubject("sbb는 spring boot board.");
//		assertEquals(1, q.getId());
//	}
	
//	@Test
//	void Jpa5() {
//		Question q = this.questionRepository.findBySubjectAndContent("sbb는 spring boot board.", "sbb 공부를 열심히 하자.");
//		assertEquals(1, q.getId());
//	}
	
//	@Test
//	void Jpa6() {
//		List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
//		Question q = qList.get(0);
//		assertEquals("sbb는 spring boot board.", q.getSubject());
//	}
	
	// Question 데이터 수정
//	@Test
//	void Jpa7() {
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);
//	}
	
	// Question 데이터 삭제.
//	@Test
//	void Jpa8() {
//		assertEquals(2,  this.questionRepository.count());
//		Optional<Question> oq = this.questionRepository.findById(1);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		assertEquals(1, this.questionRepository.count());
//	}
	
	// Answer 데이저 저장.
//	@Test
//	void Jpa9() {
//		// Question 데이터를 조회해서 해당하는 열(Answer 테이블의 question 열)에 데이터를 생성.
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됨.");
//		a.setQuestion(q);
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);
//	}
	
	// Answer 데이터 조회
//	@Test
//	void Jpa10() {
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		assertTrue(oa.isPresent());
//		Answer a = oa.get();
//		assertEquals(2, a.getQuestion().getId());
//	}
	
	// 질문 데이터에서 답변 데이터 접근.
//	@Transactional
//	@Test
//	void Jpa11() {
//		Optional<Question> oq = this.questionRepository.findById(2);
//		assertTrue(oq.isPresent());
//		Question q = oq.get();
//		
//		List<Answer> answerList = q.getAnswerList();
//		assertEquals(1, answerList.size());
//		assertEquals("네 자동으로 생성됨.", answerList.get(0).getContent());
//	}

	
}
