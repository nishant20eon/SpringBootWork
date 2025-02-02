package com.eon.email.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.eon.email.service.EmailService;

import jakarta.mail.MessagingException;

@Controller
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/form")
    public String showEmailForm() {
        return "emailForm";
    }

    @PostMapping("/send")
    public String sendEmails(@RequestParam("recipients") String recipients,
                             @RequestParam("hrNames") String hrNames,
                             @RequestParam("file") MultipartFile file,
                             Model model) {
        try {
            List<String> recipientList = List.of(recipients.split(","));
            List<String> hrNameList = List.of(hrNames.split(","));

            if (recipientList.size() != hrNameList.size()) {
                model.addAttribute("message", "Error: Number of HR names and emails must match!");
                return "emailForm";
            }

            for (int i = 0; i < recipientList.size(); i++) {
                emailService.sendEmailWithAttachment(
                        hrNameList.get(i).trim(),
                        recipientList.get(i).trim(),
                        file
                );
            }
            model.addAttribute("message", "Emails sent successfully!");
        } catch (MessagingException | IOException e) {
            model.addAttribute("message", "Error sending emails: " + e.getMessage());
        }
        return "emailForm";
    }
}
