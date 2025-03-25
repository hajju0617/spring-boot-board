package com.mysite.sbb.question.entity;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.user.entity.SiteUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	@Column(columnDefinition = "integer default 0", nullable =  false)
	private int countView;
	
	private LocalDateTime createDate;
	
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;
	
	private LocalDateTime modifyDate;
	
	@ManyToMany
	private Set<SiteUser> voter;

	public Question() {

	}

	Question(String subject, String content, SiteUser author) {
		this.subject = subject;
		this.content = content;
		this.author = author;
		this.createDate = LocalDateTime.now();
	}

	public static Question dtoToQuestionEntity(String subject, String content, SiteUser author) {
		return new Question(
				subject, content, author
		);
	}

	public static Question patchQuestion(Question question, String subject, String content) {
		if (!question.getSubject().equals(subject)) {
			question.subject = subject;
		}
		if (!question.getContent().equals(content)) {
			question.content = content;
		}
		question.modifyDate = LocalDateTime.now();
		return question;
	}
}
