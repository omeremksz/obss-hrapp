# obss-hrapp
Human Resources Job Management System

This repository contains full-stack web application project that called Human Resources Job Management System developed during my summer internhsip at OBSS 17.07.2023 - 19.08.2023. 

Technology Stack

Frontend:
- React JS
- Bootstrap
- NPM

Backend:
- Spring Boot
- Hibernate
- LDAP
- Lombok

Database:
- MySQL

Features

Regular User (Applicant) Profile
- Authentication: Users seamlessly log in via their LinkedIn accounts and fetch profile data from LinkedIn API.
- Job Application: Applicants can browse job listings and apply for desired positions.
- Application Tracking: Users can track the status of their job applications.
- Skill Addition: Applicants have the flexibility to add skills to their profiles.
HR Expert Profile
- Authentication: HR Experts are authenticated using LDAP authentication.
- Job Ad Management: Experts can create, manage, and modify job advertisements. They can set activation, expiration dates, and content for job ads.
- Application Management: HR Experts evaluate applications, change application status, and sort candidates based on skill compatibility.
- Blacklist Management: Experts have the authority to add users to the blacklist.
- Email Notifications: The system automatically sends email notifications to users upon changes in application status.
Advanced Features
- Skill-Based Sorting: The system intelligently sorts candidates based on the compatibility of their skills with job requirements.
- Free Text Search: The application incorporates a robust free text search mechanism using SOLR, enabling efficient exploration of user profiles.

