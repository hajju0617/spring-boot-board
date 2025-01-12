package com.mysite.sbb.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	
	// 페이징 처리된 글 목록 조회.
	Page<Question> findAll(Pageable pageable);
	
	// 검색.
	Page<Question> findAll(Specification<Question> specification, Pageable pageable);
	
	// Specification 대신 @Query 에노테이션을 활용해서 검색 기능을 구현.	
	@Query("SELECT distinct q "
			+ "FROM Question q " 
            + "LEFT OUTER JOIN SiteUser u1 ON q.author=u1 "
            + "LEFT OUTER JOIN Answer a ON a.question=q "
            + "LEFT OUTER JOIN SiteUser u2 ON a.author=u2 "
            + "WHERE "
            + "   q.subject LIKE %:kw% "
            + "or q.content LIKE %:kw% "
            + "or u1.username LIKE %:kw% "
            + "or a.content LIKE %:kw% "
            + "or u2.username LIKE %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);
	
	@Modifying
	@Query("UPDATE Question q SET q.countView = q.countView + 1 WHERE q.id = :id")
	@Transactional
	void updateCountView(@Param("id") Integer id);
}
