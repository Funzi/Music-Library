# Music-Library

URL for REST for all genres: /pa165/rest/genres/
This is an example output for this service with two genres already put into database: 
[{"id":1,"name":"Metal","description":"metal music"},{"id":2,"name":"Rock","description":"rock music"}]

URL for REST for one genre: /pa165/rest/genres/{id}
his is an example output for this service with id 1: 
{"id":1,"name":"Metal","description":"metal music"}

REST examples:
getAll: curl -i -X GET http://localhost:8080/pa165/rest/genres
getById: curl -i -X GET http://localhost:8080/pa165/rest/genres/${id}
create: curl -i -X POST -H "Content-Type: application/json" --data '{"name":"${name}","description":"${description}"}' http://localhost:8080/pa165/rest/genres/create
edit: curl -i -X PUT -H "Content-Type: application/json" --data '{"name":"${name}","description":"${description}"}' http://localhost:8080/pa165/rest/genres/${id}
delete: curl -i -X DELETE http://localhost:8080/pa165/rest/genres/${id}
(some genres cannot be deleted coz database foreign key)

[![Build Status](https://travis-ci.org/Funzi/Music-Library.svg?branch=master)](https://travis-ci.org/Funzi/Music-Library)
