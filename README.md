# Spring Boot with JWT (auth0/java-jwt or jjwt)

![](https://img.shields.io/badge/spring_boot-2.6.2-blue.svg)
![](https://img.shields.io/badge/java-11-blue.svg)
![](https://img.shields.io/badge/maven-3.6.3-blue.svg)
![](https://img.shields.io/badge/mysql-✓-blue.svg)
![](https://img.shields.io/badge/jwt-✓-blue.svg)

<br />

# Installation
## Install System Packages Required
We need Java 11, Maven and MySQL installed
```
sudo apt install -y openjdk-11-jdk maven
sudo apt install -y mysql-server mysql-client
```

## Create Database
Login as root:
```
sudo mysql -u root -p
```

Create database user and assign the privileges to the user created (Update `YOUR_DB_USER`, `YOUR_DB_NAME` and `YOUR_DB_PASSWORD` accordingly):
```
mysql> CREATE USER 'YOUR_DB_USER'@'%' IDENTIFIED BY 'YOUR_DB_PASSWORD';
mysql> CREATE DATABASE YOUR_DB_NAME
    -> DEFAULT CHARACTER SET utf8
    -> COLLATE utf8_general_ci;
mysql> GRANT ALL PRIVILEGES ON YOUR_DB_NAME.* to 'YOUR_DB_USER'@'%';
```

## Run the Code
```
~$ cd /path/to/spring-boot-auto0-jwt
spring-boot-auto0-jwt$ mvn clean install
spring-boot-auto0-jwt$ mvn spring-boot:run
```

You have to populate the records for user role once:
```
~$ mysql -u YOUR_DB_USER -p YOUR_DB_NAME < /path/to/roles.sql 
```

<br />

# Test the Code
## Signup
```
curl -X POST -H 'Content-Type: application/json' \
  -d '{"username":"john","password":"2simple","email":"johndoe@example.com","role":["admin"]}' \
  http://localhost:8080/auth/signup
```

## Signin
```
curl -X POST -H 'Content-Type: application/json' \
  -d '{"username":"john","password":"2simple"}' \
  http://localhost:8080/auth/signin
```

## Test API without JWT
```
curl -X GET -H 'Content-Type: application/json' \
  http://localhost:8080/api/getAuthors
```

You should get a response with [401 Unauthorized](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/401).

## Test API with JWT
You could use the `JWT_TOKEN` given from the output of [Signin](#signin):
```
curl -X GET -H 'Content-Type: application/json' \
  -H "Authorization: Bearer JWT_TOKEN" \
  http://localhost:8080/api/getAuthors
```

## Browser
Once you have signup an account, you could access the website http://localhost:8080/.  It will redirect you to login page if valid JWT is not found.
To logout the website, please go to http://localhost:8080/logout.

<br />

# Security Consideration
If you have run the code and tried the JWT, you are authorized to use the API and get the data.  You can also get the data using the `curl` command with the JWT at **_ANOTHER_** machine.  Or you can replace the JWT stored in Browser with the one from `curl` command (or vice versa), you still get the data.  As you may be aware, if one gets your JWT, one would have full access to your account and could do any operation as you even you have logout the application (before JWT is expired).

To prevent your JWT from being stolen, never use JWT without HTTPS.  Otherwise, your application is vulnerable to Man-in-the-middle (MITM) attacks.

## Store JWT in localStorage and Authorization header
In general, JWT can be sent in `Authorization` header using `Bearer` schema:
```
Authorization: Bearer <JWT>
```
A lot of examples use [localStorage](https://developer.mozilla.org/en-US/docs/Web/API/Window/localStorage).  It is [not recommended](https://cheatsheetseries.owasp.org/cheatsheets/HTML5_Security_Cheat_Sheet.html#local-storage) but it is ok if you have good Cross-Site Scripting (XSS) prevention.

## Store JWT in Cookie
This is the way this demonstration use.
```
    public String generateJwtCookie(String token) {
        return ResponseCookie
            .from("token", token)
            .maxAge(jwtExpiration)
            .httpOnly(true)
            .path("/")
            .build()
            .toString();
    }
```

In this way, the client side (Browser) does **_NOT_** need to handle the JWT nor sent the `Authorization` header (no headers property in our Axios requests).  However, general cookies can be read by JavaScript (say, `document.cookie`) and is vulnerable to XSS attack.  To avoid this, the server side need to set the `HttpOnly` flag on the cookie it creates.  It means that the cookie can **_NOT_** be read using JavaScript but can still be sent back to the server in HTTP requests. (And that's why you need a safe browser!)

Note: this spring boot application supports both ways (Using `Authorization` header or sending JWT as cookie).

## Token Lifetime
Using `HttpOnly` is not completely bulletproof.  A long-lived JWT is definitely a bad idea.  The better way is to protect your application against both XSS and CSRF:

1. [Cross-Site Request Forgery Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cross-Site_Request_Forgery_Prevention_Cheat_Sheet.html)
2. [Cross-Site Scripting Prevention Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cross_Site_Scripting_Prevention_Cheat_Sheet.html)
3. [JWT Security Best Practices](https://curity.io/resources/learn/jwt-best-practices/)

<br />

# Something Missed
1. Spring Data JPA + MySQL is used but will not be discussed here
2. Refresh Token will **_NOT_** be implemented here
2. Signup page is **_NOT_** implemented

<br />

# References
1. [Introduction to JSON Web Tokens (JWT)](https://jwt.io/introduction)
2. [Java JWT](https://github.com/auth0/java-jwt)
3. [JJWT](https://github.com/jwtk/jjwt)
4. [HttpOnly Cookie](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies#restrict_access_to_cookies)
5. [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
6. [Spring Initializr](https://start.spring.io/)

<br />

# Acknowledge
## Background Image
* [Mathematics formulas - © Guillaume Piolle](https://commons.wikimedia.org/wiki/File:Formules.JPG)
## Card Images
* [Dietmar Rabich / Wikimedia Commons / “Dülmen, Kirchspiel, Börnste, Felder und Bäume -- 2017 -- 3220-6” / CC BY-SA 4.0](https://commons.wikimedia.org/wiki/File:D%C3%BClmen,_Kirchspiel,_B%C3%B6rnste,_Felder_und_B%C3%A4ume_--_2017_--_3220-6.jpg)
* [Dietmar Rabich / Wikimedia Commons / “Dülmen, Merfeld, Feldweg am Mühlenbach -- 2021 -- 4278-80” / CC BY-SA 4.0](https://commons.wikimedia.org/wiki/File:D%C3%BClmen,_Merfeld,_Feldweg_am_M%C3%BChlenbach_--_2021_--_4278-80.jpg)
* [Dietmar Rabich / Wikimedia Commons / “Santorin (GR), Exomytis, Vlychada Beach -- 2017 -- 2999 (bw)” / CC BY-SA 4.0](https://commons.wikimedia.org/wiki/File:Santorin_(GR),_Exomytis,_Vlychada_Beach_--_2017_--_2999_(bw).jpg)

<br />

# Contribution

- Report issues
- Open pull request with improvements
- Spread the word
