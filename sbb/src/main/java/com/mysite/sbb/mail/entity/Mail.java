package com.mysite.sbb.mail.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private int authNum;

    @Column
    private LocalDateTime createDate;

    public Mail(int authNum) {
        this.authNum = authNum;
        this.createDate = LocalDateTime.now();
    }

    public Mail() {

    }

    public static Mail toMailEntity(int authNum) {
        return new Mail(authNum);
    }
}
