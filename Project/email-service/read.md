# Job Application Email Sender

This Spring Boot application allows users to send emails to multiple HRs with a resume attached, based on data from a CSV file. It leverages Spring Boot's capabilities for sending emails, handling file uploads, and processing CSV files.

## Project Structure

- **EmailController.java**: Handles form submissions for sending emails via the web interface.
- **CSVController.java**: Reads the CSV file containing HR details and sends emails with attached resumes.
- **EmailService.java**: Sends emails with attachments using JavaMailSender.
- **CSVService.java**: Handles email sending logic for processing CSV data.
- **application.properties**: Contains configuration properties, such as the file paths for CSV and resume.
- **emailForm.html**: Provides an HTML form for manually entering HR details and uploading a resume.

## API Endpoints

### 1. `/email/form` (GET)

- **Purpose**: Displays the form where users can input HR email addresses, names, and upload their resumes.
- **Request Method**: GET
- **Response**: A form that allows users to enter HR details and upload a resume.

### 2. `/email/send` (POST)

- **Purpose**: Sends emails to HRs with the uploaded resume as an attachment.
- **Request Method**: POST
- **Request Body**:
  - **recipients**: A comma-separated list of HR email addresses.
  - **hrNames**: A comma-separated list of HR names corresponding to the emails.
  - **file**: The resume file to be attached.
- **Response**: Success message if emails are sent, error message if there is an issue.

### 3. `/sendEmails` (GET)

- **Purpose**: Reads the HR data from a CSV file, sends emails to each HR with the resume attached.
- **Request Method**: GET
- **Request Parameters**:
  - The CSV file path and resume file path are hardcoded in the `application.properties` file.
- **Response**: A message indicating whether the emails were sent successfully or if there was an error.

## Configuration

### `application.properties`

```properties
# File Paths
file.csv.path=path_to_your_csv_file
file.resume.path=path_to_your_resume_file

spring.application.name=email-service
#eon@9934Eon@9934
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=nishant20eon@gmail.com
spring.mail.password=tvei pjlz inbq irpf
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

file.csv.path=src/main/resources/files/hr_data.csv
file.resume.path=src/main/resources/files/Resume.pdf



