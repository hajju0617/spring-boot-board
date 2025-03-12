package com.mysite.sbb.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class MailController {
    private final MailService mailService;

    @PostMapping("/verification")
    public String sendMessage(@RequestParam("email") String email) {
        mailService.sendToEmail(email);
        return "전송 되었습니다.";
    }

    @GetMapping("/verification")
    public boolean verificationEmail(@RequestParam("authNum") int authNum) {
        return mailService.verifiedCode(authNum);
    }
}
