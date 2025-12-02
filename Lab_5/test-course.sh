#!/usr/bin/env bash

echo "=== COURSE-SERVICE TESTS (localhost:8083) ==="

# Create first course
curl -X POST http://localhost:8083/courses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "CS101",
    "name": "Intro to Computer Science",
    "description": "Basic programming and CS concepts"
  }'
echo -e "\n--- created course 1 ---\n"

# Create second course
curl -X POST http://localhost:8083/courses \
  -H "Content-Type: application/json" \
  -d '{
    "code": "DB201",
    "name": "Databases",
    "description": "Relational models, SQL and transactions"
  }'
echo -e "\n--- created course 2 ---\n"

# List all courses
curl -X GET http://localhost:8083/courses
echo -e "\n--- list courses ---\n"

# Get course by id = 1
curl -X GET http://localhost:8083/courses/1
echo -e "\n--- get course 1 ---\n"

# Get course by id = 2
curl -X GET http://localhost:8083/courses/2
echo -e "\n--- get course 2 ---\n"
