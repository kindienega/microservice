#!/bin/bash

# Navigate to the parent directory of your multi-module project

# Build each module individually
modules=("ApiGateway" "AuthService" "InventoryManagement" "OrderService" "ServiceDiscovery")

for module in "${modules[@]}"; do
    echo "Building $module..."
    cd "$module"
    mvn clean install
    cd ..
done

# Once all modules are built, you can package the entire application
echo "Packaging the application..."
mvn  clean package -DskipTests
