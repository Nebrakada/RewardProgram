**Reward points calculator program**
is a simple app calculating bonus points for customers for their transactions made.

**Assignment instructions:**
A retailer offers a rewards program to its customers, awarding points based on each
recorded purchase.  

A customer receives 2 points for every dollar spent over $100 in each transaction,
plus 1 point for every dollar spent over $50 in each transaction
(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).  

Given a record of every transaction during a three-month period, calculate the reward
points earned for each customer per month and total.


**a bit of explanation**
* I have on purpose used custom-made validation instead of build-in Spring/Hibernate @Valid and BindingResult option
* package names are simplified instead of going full DDD/feature naming or going different modules for each section, but it should be easy to divide them if needed
* there are unit tests and e2e / controller tests in test packages
* Swagger/OpenAPI can be accessed by URL:  `http://localhost:8080/swagger-ui.html`
* you can access H2 console on `http://localhost:8080/h2`

**to run the app**
* make sure you have both maven and java 11+ installed and configured
* run `mvn clean install` in the terminal (or use Intellij to do it) to build jar file
* run `mvn spring-boot:run` to run the app in the embedded tomcat server (or use Intellij to do it)
