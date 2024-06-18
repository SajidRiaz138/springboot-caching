# Read Me First
Spring boot and Caffeine cache based Rest Api's with MySql database

* Make sure MySql Server is running on localhost
* Basic unit tests are added

# Rest endpoints
* curl -X POST http://localhost:8080/customers/save \
  -H "Content-Type: application/json" \
  -d '{
  "customerName": "John Doe",
  "address": "123 Main St"
  }'
* curl -X PUT http://localhost:8080/customers/update   -H "Content-Type: application/json"   -d '{
  "customerId":4, "customerName": "John Doe",
  "address": "1247 Main St"
  }'
* curl http://localhost:8080/customers/{id} (get customer)
* curl -X DELETE "http://localhost:8080/customers/delete/1" (delete customer)
* curl http://localhost:8080/customers/customer/{key} (Invalidate cache key)
* curl http://localhost:8080/customers/customer (Invalidate cache)



