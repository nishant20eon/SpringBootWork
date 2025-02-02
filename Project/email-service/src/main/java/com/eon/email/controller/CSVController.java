package com.eon.email.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eon.email.service.CSVService;

import jakarta.mail.MessagingException;

@RestController
public class CSVController {

    @Autowired
    private CSVService csvService;
    

    @Value("${file.csv.path}")
    private String csvFilePath;

    @Value("${file.resume.path}")
    private String resumeFilePath;

    @GetMapping("/sendEmails")
    public ResponseEntity<String> sendEmails(Model model) {
        try {

            // Create File objects for the files
            File csvFile = new File(csvFilePath);
            File resumeFile = new File(resumeFilePath);

            // Read the CSV file and send emails to the HRs
            List<String[]> hrData = readCSV(csvFile);

            // Process each HR data and send email with attachment
            for (String[] hr : hrData) {
                String hrName = hr[0].trim();
                String hrEmail = hr[1].trim();

                // Send email with resume attachment
                csvService.sendEmailWithAttachment(hrName, hrEmail, resumeFile);
            }

            return ResponseEntity.ok("Email sent successfully!");
        } catch (IOException | MessagingException e) {
        	return ResponseEntity.status(500).body("Error sending emails: " + e.getMessage());
        }
    }

    // Utility method to read CSV file
    private List<String[]> readCSV(File csvFile) throws IOException {
        List<String[]> hrData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                hrData.add(line.split(","));
            }
        }
        return hrData;
    }
}
