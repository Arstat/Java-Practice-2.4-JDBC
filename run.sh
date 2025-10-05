#!/bin/bash

echo "Select application to run:"
echo "1. Part A - Employee Data Fetch"
echo "2. Part B - Product CRUD"
echo "3. Part C - Student Management"
read -p "Choice: " choice

case $choice in
    1) cd part-a && java -cp .:../lib/mysql-connector-j-8.0.33.jar EmployeeDataFetch ;;
    2) cd part-b && java -cp .:../lib/mysql-connector-j-8.0.33.jar ProductCRUD ;;
    3) cd part-c && java -cp .:../lib/mysql-connector-j-8.0.33.jar StudentView ;;
    *) echo "Invalid choice" ;;
esac
