# Spring Boot Boilerplate
This is Spring Boot boilerplate project with JWT based authentication and Role based authorization.

# Tools and Technologies
1. Java 8
2. Spring Boot 2.5.2
3. MySQL
4. Redis

# Features
 - Signup 
 (User will get JWT token which is valid for 5 min and Welcome email with 6 digit OTP)
 - Login
 - Email verification (User won't be able to access other resources without verifying email)
 - Logout
 - Update user name

# API Detail
1. User Signup
>      curl --location --request POST 'http://localhost:8080/api/auth/signup' \
>     --header 'Content-Type: application/json' \
>     --data-raw '{
>         "name": "NewTest User",
>         "email": "newterb@mailinator.com",
>         "password": "Password@123!"
>     }'

2. Log in

>     curl --location --request POST 'http://localhost:8080/api/auth/login' \
>     --header 'Content-Type: application/json' \
>     --data-raw '{
>         "email": "newterb@mailinator.com",
>         "password": "Password@123!"
>     }'

3. Refresh Access Token

>     curl --location --request POST 'http://localhost:8080/api/auth/token' \
>     --header 'Content-Type: application/json' \
>     --data-raw '{
>         "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlcnJyQG1haWwuY29tIiwiaWF0IjoxNjI1Mjk4ODExLCJleHAiOjE2MjUyOTkxMTF9.NULYSv_ESeYTgg31oTqzumSPUHRmkE-Ce88RUPZGT2EX5XEk7K7LJpO4-THWnbiOL9T94JMJ30Dvzgz07gffmg"
>     }'

4. Log out

>     curl --location --request POST 'http://localhost:8080/api/auth/logout' \
>     --header 'Content-Type: application/json' \
>     --header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXd0ZXJiQG1haWxpbmF0b3IuY29tIiwiaWF0IjoxNjI1MzE0MDEzLCJleHAiOjE2MjUzMTQzMTN9.-YkkiZ0skRuhkzSBT68_m1WtOZ-YlMmpW1ciIYmcsrU9bxwMcBcnLilGJJxsWcNvYOpxJBLIlfLIC5QrgqJIPQ'

5. Verify Email
>     curl --location --request POST 'http://localhost:8080/api/auth/verify/email' \
>     --header 'Content-Type: application/json' \
>     --header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXd0ZXJiQG1haWxpbmF0b3IuY29tIiwiaWF0IjoxNjI1MzE0MDEzLCJleHAiOjE2MjUzMTQzMTN9.-YkkiZ0skRuhkzSBT68_m1WtOZ-YlMmpW1ciIYmcsrU9bxwMcBcnLilGJJxsWcNvYOpxJBLIlfLIC5QrgqJIPQ'\--data-raw '{"verificationCode": "133967"}'

6. Update User Name

>     curl --location --request PATCH 'http://localhost:8080/api/users' \
>     --header 'Content-Type: application/json' \
>     --header 'authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXd0ZXJiQG1haWxpbmF0b3IuY29tIiwiaWF0IjoxNjI1MzE0MzQ0LCJleHAiOjE2MjUzMTQ2NDR9.Xe_2MdCVHBIfa0v9G4hrto0EluYltyEryWDlvopQ90ez5nqVqfDGEC035mnzU8J-xsu6fyUAh81XpRbEczHhbg'\--data-raw '{"name": "again Updated name"}'
