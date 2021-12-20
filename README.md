# Book details example 


This is the source code for the "Getting started with GraphQL Java and Spring Boot" which 
is available here: https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/ 

### Sample queries

```
mvn clean install
mvn spring-boot:run
```

Navigate to http://localhost:8080/graphiql and type in the queries below:

bookById(id: ID): Book
```
{
  bookById(id: "book-1"){
    id
    name
    pageCount
    author {
      firstName
      lastName
    }
  }
}
```

authorById(id: ID): Author
```
{
  authorById(id: "author-1"){
    id
    firstName
    lastName
  }
}
```

authorByFirstName(firstName: String): [Author]
```
{
  authorByFirstName(firstName: "Joanne"){
    id
    firstName
    lastName
  }
}
```

authorByName(firstName: String, lastName: String): [Author]
```
{
  authorByName(firstName: "Joanne", lastName: "Rowling"){
    id
    firstName
    lastName
  }
}
```
