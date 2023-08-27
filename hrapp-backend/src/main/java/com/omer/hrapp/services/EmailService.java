package com.omer.hrapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String status, String applicantName, String jobTitle, String specialistName){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("omeremksz@gmail.com");
        message.setTo(toEmail);
        if (status.equals("Processing")) {
            String subject ="Your Application Status is Changed: Processing";
            String body = "Dear "+applicantName +",\n" + "\n"+
                    "Congratulations on your application for the "+jobTitle+ " role.\n" + "\n"+
                    "We would like to inform you that your application has been processed.\n" + "\n"+
                    "Best regards,\n"+ "\n"+
                    specialistName;
            message.setText(body);
            message.setSubject(subject);
        }
        else if (status.equals("Accepted")) {
            String subject ="Your Application Status is Changed: Accepted";
            String body = "Dear "+applicantName +",\n" + "\n"+
                    "Congratulations! We are pleased to inform you that your application for the "+jobTitle+ " position has been accepted.\n" + "\n"+
                    "We were impressed by your qualifications and believe you would be a great fit for our team.\n" + "\n"+
                    "Best regards,\n"+ "\n"+
                    specialistName;
            message.setText(body);
            message.setSubject(subject);
        } else if (status.equals("Rejected")) {
            String subject ="Your Application Status is Changed: Rejected";
            String body = "Dear "+applicantName +",\n" + "\n"+
                    "Thank you for your interest in the "+jobTitle+ ". We appreciate the time and effort you put into your application.\n" +
                    "After careful review, we have chosen to pursue other candidates who closely match our requirements. " +
                    "Your qualifications are impressive, and we encourage you to consider future opportunities with us.\n" + "\n"+
                    "Best regards,\n"+ "\n"+
                    specialistName;
            message.setText(body);
            message.setSubject(subject);
        }

        javaMailSender.send(message);
    }
}
