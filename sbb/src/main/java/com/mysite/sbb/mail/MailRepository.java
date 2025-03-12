package com.mysite.sbb.mail;

import com.mysite.sbb.mail.entity.Mail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;


public interface MailRepository extends JpaRepository<Mail, Integer> {
    Mail findByAuthNum(int authNum);

    @Modifying
    @Transactional
    @Query("DELETE FROM Mail m WHERE m.createDate < :timeLimit")
    void deleteExpiredAuthNums(@Param("timeLimit") LocalDateTime timeLimit);
}
