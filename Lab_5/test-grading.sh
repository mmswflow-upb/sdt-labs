#!/usr/bin/env bash

echo "=== GRADING-SERVICE TESTS (localhost:8084) ==="

# Create first grade (student=1, professor=1, course=1)
curl -X POST http://localhost:8084/grades \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "professorId": 1,
    "courseId": 1,
    "value": 9.5
  }'
echo -e "\n--- created grade 1 ---\n"

# Create second grade (student=1, professor=2, course=2)
curl -X POST http://localhost:8084/grades \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "professorId": 2,
    "courseId": 2,
    "value": 8.75
  }'
echo -e "\n--- created grade 2 ---\n"

# Create third grade (student=2, professor=1, course=1)
curl -X POST http://localhost:8084/grades \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 2,
    "professorId": 1,
    "courseId": 1,
    "value": 7.0
  }'
echo -e "\n--- created grade 3 ---\n"

# List ALL grades
curl -X GET "http://localhost:8084/grades"
echo -e "\n--- list all grades ---\n"

# Get grades by studentId = 1
curl -X GET "http://localhost:8084/grades?studentId=1"
echo -e "\n--- grades for student 1 ---\n"

# Get grades by studentId = 2
curl -X GET "http://localhost:8084/grades?studentId=2"
echo -e "\n--- grades for student 2 ---\n"

# Get grades by professorId = 1
curl -X GET "http://localhost:8084/grades?professorId=1"
echo -e "\n--- grades given by professor 1 ---\n"

# Get grades by professorId = 2
curl -X GET "http://localhost:8084/grades?professorId=2"
echo -e "\n--- grades given by professor 2 ---\n"

# Get grades by courseId = 1
curl -X GET "http://localhost:8084/grades?courseId=1"
echo -e "\n--- grades for course 1 ---\n"

# Get grades by courseId = 2
curl -X GET "http://localhost:8084/grades?courseId=2"
echo -e "\n--- grades for course 2 ---\n"

# Update grade with id = 1 (set value to 10.0)
curl -X PUT http://localhost:8084/grades/1 \
  -H "Content-Type: application/json" \
  -d '{
    "studentId": 1,
    "professorId": 1,
    "courseId": 1,
    "value": 10.0
  }'
echo -e "\n--- updated grade 1 ---\n"

# Check updated grade by querying student 1 again
curl -X GET "http://localhost:8084/grades?studentId=1"
echo -e "\n--- grades for student 1 after update ---\n"
