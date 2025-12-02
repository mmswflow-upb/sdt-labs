#!/usr/bin/env bash

echo "=== STUDENT-SERVICE TESTS (localhost:8081) ==="

# Create first student
curl -X POST http://localhost:8081/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Mario",
    "lastName": "Sakka",
    "email": "mario.sakka@example.com"
  }'
echo -e "\n--- created student 1 ---\n"

# Create second student
curl -X POST http://localhost:8081/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Ana",
    "lastName": "Popescu",
    "email": "ana.popescu@example.com"
  }'
echo -e "\n--- created student 2 ---\n"

# List all students
curl -X GET http://localhost:8081/students
echo -e "\n--- list students ---\n"

# Get student by id = 1
curl -X GET http://localhost:8081/students/1
echo -e "\n--- get student 1 ---\n"

# Get student by id = 2
curl -X GET http://localhost:8081/students/2
echo -e "\n--- get student 2 ---\n"
