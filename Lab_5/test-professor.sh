#!/usr/bin/env bash

echo "=== PROFESSOR-SERVICE TESTS (localhost:8082) ==="

# Create first professor
curl -X POST http://localhost:8082/professors \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com"
  }'
echo -e "\n--- created professor 1 ---\n"

# Create second professor
curl -X POST http://localhost:8082/professors \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Mihai",
    "lastName": "Ionescu",
    "email": "mihai.ionescu@example.com"
  }'
echo -e "\n--- created professor 2 ---\n"

# List all professors
curl -X GET http://localhost:8082/professors
echo -e "\n--- list professors ---\n"

# Get professor by id = 1
curl -X GET http://localhost:8082/professors/1
echo -e "\n--- get professor 1 ---\n"

# Get professor by id = 2
curl -X GET http://localhost:8082/professors/2
echo -e "\n--- get professor 2 ---\n"
