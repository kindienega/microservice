package et.com.gebeya.emailservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String emailFrom;

    private final JavaMailSender emailSender;

    public void sendEmail(String to,String subject, String text){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);
        emailSender.send(message);
    }
}
