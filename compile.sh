#!/bin/bash
echo "Compiling JDBC Applications..."

cd part-a
javac -cp .:../lib/mysql-connector-j-8.0.33.jar *.java && echo "✓ Part A done"
cd ../part-b
javac -cp .:../lib/mysql-connector-j-8.0.33.jar *.java && echo "✓ Part B done"
cd ../part-c
javac -cp .:../lib/mysql-connector-j-8.0.33.jar *.java && echo "✓ Part C done"
cd ..

echo "Compilation complete!"
