# Attendance-Tracker
## Green Fox Academy :fox_face: Panthera Ripped - Java SpringBoot Application
Final project at the Green Fox Academy.  
### CtrlAltElite Team
**Project Leader:**  
Jakub Korch - [GitHub](https://github.com/j-cup)  
<br>
**Members:**  
Radovan Kosc - [GitHub](https://github.com/radovankosc)  
Anna Kasikova - [GitHub](https://github.com/anna9393)  
Radovan Lipka - [GitHub](https://github.com/Radoo92)  
Adela Simkova - [GitHub](https://github.com/Karcoolka)  
Jakub Weiser - [GitHub](https://github.com/weiserjakub)  
<br><br>
## About the project
Attendance Tracker is an app for small/medium companies to track their employee's attendance.
The application can register new employees, confirm the registration via email, login user and admin, and track the user's attendance with respect to weekends, holidays, and medical issues.
Attendance Tracker also includes creating each user's supervisor to prove or deny the employee's attendance.
  
## Environment Variables

**Database connection**

First create database "CtrlAltElite" in mySQL

| Key | Value |
| --- | ----- | 
|DATASOURCE_URL | jdbc:mysql://localhost:3306/attendance?serverTimezone\=UTC |
|DATASOURCE_USERNAME | *your local mysql username* |
|DATASOURCE_PASSWORD | *your local mysql password* |
|HIBERNATE_DIALECT | org.hibernate.dialect.MySQL5Dialect |
|MAIL_HOST | smtp.gmail.com |
|MAIL_PORT | 587 |
|MAIL_USERNAME | *mail username* |
|MAIL_PASSWORD | *mail password* |
|SIGNING_KEY | *signing key* |

<br><br>

## Endpoints

**HomeController:**
<br>
GET /hello<br>
GET /user<br>
GET /admin<br>
GET /populate<br>

**AdminController:**
<br>
PATCH /change-approver<br>

**UserController:**
<br>
GET /all<br>
GET /{id}<br>
POST /login<br>

**UserProfileController:**
<br>
GET /profile<br>

**RegistrationController:**
<br>
POST /register<br>
GET /confirm/{activationCode}<br>

**ResetController:**
<br>
POST /reset<br>
POST /reset/{resetCode}<br>

**RecordController:**
<br>
DELETE /tracking_record/{id}<br>
POST /create-record<br>
POST /edit-record/{id}<br>

**AttachmentController:**
<br>
POST /upload<br>
GET /download/{fileName}<br>

**PeriodController:**
<br>
GET /list-periods<br>
GET /list-periods/{id}<br>
PATCH /submit-period<br>
PATCH /close-period<br>

**TrackPeriodController:**
<br>
POST /request/tracked/period<br>

**HolidayController:**
<br>
POST /submit-holiday<br>
DELETE /delete-holiday/{id}
