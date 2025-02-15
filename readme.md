# Application Setup Guide

## Prerequisites

- Download and install **IntelliJ IDEA**: [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/)

## Steps to Set Up the Application

1. **Extract Project Files**
   - Extract the `.zip` folder containing the project files.

2. **Open the Project in IntelliJ IDEA**
   - Open IntelliJ IDEA and load the extracted project.

3. **Set Up JDK**
   - Ensure **JDK version 17** is configured for the project.

4. **Update Maven Dependencies**
   - Refresh and update Maven dependencies to ensure all required libraries are downloaded.

5. **Connect to the PostgreSQL Database**
   - Navigate to `Database` in IntelliJ IDEA:
     - **Add Database** ? Select **PostgreSQL**.
     - Enter the following details:
       - **Database Name**: `dsdb_4w9a`
       - **Username**: `dsuser`
       - **Password**: `xDu55OhxyCgWlAvdRQcbOPzY47qR8cV0`
       - **URL**: `jdbc:postgresql://dpg-cu2fbadds78s73dsf4a0-a.oregon-postgres.render.com:5432/dsdb_4w9a`
     - Test the connection to ensure it succeeds.
     - Apply and save the configuration.

6. **Run the Application**
   - Use IntelliJ IDEA to run the application.

## Access the Application

- Once the application is running, access it at: [http://localhost:8080](http://localhost:8080)

