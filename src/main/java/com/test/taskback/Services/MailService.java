package com.test.taskback.Services;

import com.test.taskback.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@PropertySource("classpath:application.properties")
@Service
public class MailService {

      @Autowired
      JavaMailSender javaMailSender;
         @Autowired
         Environment env;

         public void send(User user, String message, String subject) {
             MimeMessage mimeMessage = javaMailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
             try {
                 mimeMessage.setFrom(new InternetAddress(env.getProperty("spring.mail.username")));
                 helper.setTo(user.getEmail());
                 helper.setText(message,true);
                 helper.setSubject(subject);
             } catch (MessagingException e) {
                 e.printStackTrace();
             }
                 javaMailSender.send(mimeMessage);

         }
}
