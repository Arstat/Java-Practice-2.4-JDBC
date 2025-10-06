#!/bin/bash
# Quick start script - run this after Codespace restarts

# Start MySQL
sudo service mysql start 2>/dev/null || sudo systemctl start mysql 2>/dev/null

# Wait a moment
sleep 2

# Check status
if mysql -u javauser -pjava123 -e "SELECT 1;" > /dev/null 2>&1; then
    echo "MySQL is ready!"
    ./compile.sh
    ./run.sh
else
    echo "MySQL may need setup. Run setup commands."
    cd .devcontainer
    ./setup.sh
    cd ..
    ./compile.sh
    ./run.sh   
fi
