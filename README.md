# Reservation

### Run example using command line

>set JAVA_HOME=C:\localinstalls\jdk-11.0.2  
>mvn spring-boot:run 

### Test example using browser or postman

##### Initialise guest price quotes  

[http://localhost:8080/initGuestPriceQuotes?guestPriceQuotes=23,45,155,374,22,99,100,101,115,209](http://localhost:8080/initGuestPriceQuotes?guestPriceQuotes=23,45,155,374,22,99,100,101,115,209)

##### Input premium and economy room availability count to get room usage details
[http://localhost:8080/getRoomUsageDetails?inputPremiumRoomCount=1&inputEconomyRoomCount=1](http://localhost:8080/getRoomUsageDetails?inputPremiumRoomCount=1&inputEconomyRoomCount=1)


##### Test report generation
>mvn surefire-report:report

Report can be found at **`target\site\surefire-report.html`**

#### Swagger integration

http://localhost:8080/swagger-ui.html

### 2 ways to create docker image

#### 1 Create docker image using buildpack

>mvn spring-boot:build-image

#### 2 Create docker image usig multistage Dockerfile

>docker build -t santaji1986/reservation:latest .

### Run the docker image using

>docker run -d -p 8080:8080 santaji1986/reservation