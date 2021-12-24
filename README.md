# Distributed Airways

## TEMPORARY INFO

### Getting started

```
./run-services.sh
```

Note: Wait for 1-2 mins after Broker is started to make a graphql request at http://localhost:8080/graphiql, since [we need to wait for airline services to be registered](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html#_why_is_it_so_slow_to_register_a_service).

#### Sample query
Request
```graphql
{
  flights(date: "2021-12-24", sourceCity:"Dublin", destinationCity:"London") {
    id,
    time,
    dayOfWeek,
    price
  }
}
```
Response
```json
{
  "data": {
    "flights": [
      {
        "id": "1",
        "time": [
          "08:00",
          "09:00"
        ],
        "dayOfWeek": [
          "Monday",
          "Friday"
        ],
        "category": [
          "None",
          "Silver",
          "Gold",
          "Platinum"
        ],
        "price": [
          100,
          90,
          80,
          70
        ]
      }
    ]
  }
}
```

## Team

- [Ahmed Jouda](https://github.com/AhmedJouda2000) 
- [Chee Guan (Jason) Tee](https://github.com/AmplifiedHuman)
- Noor Bari
- [Rajit Banerjee](https://github.com/rajitbanerjee/)

## Acknowledgements

- [Assoc. Prof. Rem Collier, UCD](https://people.ucd.ie/rem.collier)

