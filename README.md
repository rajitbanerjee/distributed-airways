# Distributed Airways

#### TEMPORARY INFO

- Emirates
    - For testing, mongodb must be running in the background on default port.
      ```
      docker run --name mongodb -d -p 27017:27017 mongo
      ```
    - No database connection code is required since [Spring Boot handles that](https://www.mongodb.com/compatibility/spring-boot).
    - See https://spring.io/projects/spring-data for Spring Data abstraction for database.
    - See `emirates/src/main/resources/flights.http` for sample client side request to emirates graphql server.


### Team

- [Ahmed Jouda](https://github.com/AhmedJouda2000) 
- [Chee Guan (Jason) Tee](https://github.com/AmplifiedHuman)
- Noor Bari
- [Rajit Banerjee](https://github.com/rajitbanerjee/)

### Acknowledgements

- [Assoc. Prof. Rem Collier, UCD](https://people.ucd.ie/rem.collier)

