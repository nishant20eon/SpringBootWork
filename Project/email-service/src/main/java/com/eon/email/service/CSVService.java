package com.eon.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class CSVService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailWithAttachment(String hrName, String to, File resumeFile) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Application for Java Developer Role");

        // Plain text email body
        String body = String.format(
                "Hi %s,\n\n" +
                "I hope you're doing well. I am currently looking for a Java Developer role and wanted to reach out to see if there are any opportunities available.\n\n" +
                "With 3.5+ years of experience as a Java Developer, I have worked extensively with Java, Spring Boot, Microservices, SQL Server, and Bitbucket. " +
                "I have also contributed to REST APIs, performance optimization, and database management. I am passionate about building scalable and efficient solutions.\n\n" +
                "I would truly appreciate it if you could refer me for any suitable opportunities or connect me with the hiring team. " +
                "I have attached my resume for your reference. Please let me know if there’s any additional information I can provide.\n\n" +
                "Looking forward to your response.\n\n" +
                "Best regards,\n" +
                "Nishant Kumar\n" +
                "📞 8109650083\n" +
                "LinkedIn Profile: https://www.linkedin.com/in/nishant-kumar-profile",
                hrName
        );

        helper.setText(body); // Set plain text body (no HTML)

        // Attach the resume file
        helper.addAttachment(resumeFile.getName(), resumeFile);

        mailSender.send(message);
        System.out.println("Email sent to " + to);
    }
}
