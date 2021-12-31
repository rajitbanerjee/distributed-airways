## Distributed Airways

### Requirements

- Docker Desktop for Mac/Windows
- JDK 8 and Apache Maven
- Node.js [_Note_: This is only required if starting the development server for the `ui/` module. Not necessary when running the services with `docker-compose`.]

### Getting Started

- TLDR;

  - Clean build and start:
    ```
    time ./clean-start.sh && sleep 25 && open http://localhost:1337
    ```
  - [Optional] Quicker subsequent build alternative:

    ```
    docker-compose down && docker-compose up -d && sleep 25 && open http://localhost:1337
    ```

- Compile and run all services in Docker (detached mode) using:

  ```
  ./clean-start.sh
  ```

  This is equivalent to running:

  ```
  mvn clean install
  docker-compose down --remove-orphans
  docker-compose build --no-cache
  docker-compose up -d
  ```

- To view the logs for any particular container, use:
  ```
  docker-compose logs $CONTAINER_NAME
  ```
- [Optional] To run the basic CLI client, use:
  ```
  mvn exec:java -pl cli-client
  ```
- To view the frontend, navigate to http://localhost:1337.

### Team

- [Ahmed Jouda](https://github.com/AhmedJouda2000)
- [Chee Guan (Jason) Tee](https://www.jasontcg.com)
- [Noor Bari](https://github.com/noorb98)
- [Rajit Banerjee](https://rajitbanerjee.com)

### Acknowledgements

- [Assoc. Prof. Rem Collier, UCD](https://people.ucd.ie/rem.collier)
