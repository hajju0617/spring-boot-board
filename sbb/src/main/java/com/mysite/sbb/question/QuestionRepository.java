package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;

import java.util.List;



public interface QuestionRepository extends JpaRepository<Question, Integer>{
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	
	Page<Question> findAll(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Question q SET q.countView = q.countView + 1 WHERE q.id = :id")
	@Transactional
	void updateCountView(@Param("id") Integer id);
}
