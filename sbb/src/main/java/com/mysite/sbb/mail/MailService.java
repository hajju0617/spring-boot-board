package com.mysite.sbb.mail;



import com.mysite.sbb.mail.entity.Mail;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.mysite.sbb.mail.entity.Mail.toMailEntity;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;
    public int makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }
        return Integer.parseInt(randomNumber);
    }

    public void sendToEmail(String email) {
        makeRandomNumber();
        String setFrom = "hajju0617@naver.com"; // email-config에 설정한 자신의 이메일 주소
        String toMail = email;
        String title = "이메일 인증.";
        int authNumber = makeRandomNumber();
        String content =
                "안녕하세요." + 	//html 형식
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "해당 인증번호를 입력해주세요";
        mailSend(setFrom, toMail, title, content);
        mailRepository.save(toMailEntity(authNumber));

    }
    public void mailSend(String setFrom, String toMail, String title, String content) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");

            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            javaMailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
    }

    public boolean verifiedCode(int authNum) {
        cleanExpiredAuthNums();
        Mail mail = mailRepository.findByAuthNum(authNum);
        return mail != null;
    }
    public void cleanExpiredAuthNums() {
        mailRepository.deleteExpiredAuthNums(LocalDateTime.now().minusMinutes(2));  // 현재 시각에서 2분을 뺀 시각
    }
}
