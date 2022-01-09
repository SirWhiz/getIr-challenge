# GetIr challenge by Alvaro Ferrera

## Brief description
This is my solution to the ReadingIsGood Java case study. I have opted to use
Java alongside Sring Boot.
For data storage I've used, as advised, MongoDB.

A MongoDB database "readingisgood_db" is needed to run this application

I have build a restful API that allows customers to register, login, perform orders,
add books, update books' stock, list all customer's orders, view monthly statistics
and more.
I have used JUnit as my test suite.

## API
To make it as easy as possible I have included a file in the repository:

```sh
GetIr Alvaro Ferrera.postman_collection.json
```

That can be directly imported into Postman as a collection and it has all the
HTTP requests already saved, named, and it also automatically includes the bearer
token in each request once you log in.

## Swagger
I have included Swagger in my project and it is accessible using the following URL:

```sh
http://localhost:8080/swagger-ui/index.html#/
```
