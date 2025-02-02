package com.eon.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String hrName, String to, MultipartFile file) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Application for Java Developer Role");

        // Construct the email body dynamically with HTML formatting
        String body = String.format(
                "<p>Hi %s,</p>" +
                "<p>I hope you're doing well. I am currently looking for a Java Developer role and wanted to reach out to see if there are any opportunities available.</p>" +
                "<p>With 3.5+ years of experience as a Java Developer, I have worked extensively with Java, Spring Boot, Microservices, SQL Server, and Bitbucket. I have also contributed to REST APIs, performance optimization, and database management. I am passionate about building scalable and efficient solutions.</p>" +
                "<p>I would truly appreciate it if you could refer me for any suitable opportunities or connect me with the hiring team. I have attached my resume for your reference. Please let me know if there’s any additional information I can provide.</p>" +
                "<p>Looking forward to your response.</p>" +
                "<p>Best regards,<br>" +
                "Nishant Kumar<br>" +
                "📞 8109650083<br>" +
                "<a href='https://www.linkedin.com/in/nishant-kumar-profile' target='_blank'>🔗 LinkedIn Profile</a></p>",
                hrName
        );

        helper.setText(body, true); // Enable HTML formatting

        // Attach uploaded resume
        helper.addAttachment(file.getOriginalFilename(), file);

        mailSender.send(message);
        System.out.println("Email sent to " + to);
    }

}
