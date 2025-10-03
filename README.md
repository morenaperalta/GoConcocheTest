# GoConcoche
## 📄 Intro
Welcome to the backend API GoConCoche, a web app designed to let user register and rent out their 
car (or fleet of cars) or rent a car.
The system enables users to search for, book, and manage car-sharing reservations at specific 
locations; allow car owners to manage their rental offers; and provide administrators with tools 
to oversee vehicle fleets and booking rules. It also includes advanced features such as secure 
authentication using JWT. The project is implemented using Java 21, Maven, and MySQL, with a strong 
focus on modularity, security, and efficiency.

## 🖥 Technologies

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![draw.io](https://img.shields.io/badge/draw.io-F08705?style=for-the-badge&logo=diagramsdotnet&logoColor=white)
![Cloudinary](https://img.shields.io/badge/cloudinary-3448C5?style=for-the-badge&logo=cloudinary&logoColor=white)
![Swagger](https://img.shields.io/badge/swagger-%2385EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black)

## 🛞 First Steps

### 🧱 Clone the Repository
```shell
git clone https://github.com/More-ThanCode/GoConcoche.git
```

### 🚀 Run the application
```shell
mvn spring-boot:run
```

## ⬇️ Main API Endpoints

### Registration / Login
- POST http://localhost:8080/api/auth/register registration of users
- POST http://localhost:8080/api/auth/login for login
- POST http://localhost:8080/api/auth/refresh for refresh token
- POST http://localhost:8080/api/logout for logout


### Vehicle
- GET http://localhost:8080/api/vehicles to get all vehicles (only for ADMIN)
- GET http://localhost:8080/api/vehicles/my to get own vehicles (logged-in OWNER gets their own vehicles)
- GET http://localhost:8080/api/vehicles/{id} to get vehicle by OWNER id (ADMIN only)
- POST http://localhost:8080/api/vehicles to create a new vehicle (OWNER)
- PUT http://localhost:8080/api/vehicles/{id} to update vehicle by ID (OWNER's own vehicle)
- DELETE http://localhost:8080/api/vehicles/{id} to delete vehicle by ID (OWNER's own vehicle)

### Rental offers
- GET http://localhost:8080/api/rental-offers to get all rental offers (OWNER)
- GET http://localhost:8080/api/rental-offers/my-offers OWNER gets their own offers
- POST http://localhost:8080/api/rental-offers to create rental offer
- PUT http://localhost:8080/api/rental-offers/{id} to update rental offers by ID
- DELETE http://localhost:8080/api/rental-offers/{id} to delete rental offer by ID (OWNER)
- FILTER BY CITY: http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?city=Valencia
- FILTER BY START: http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?startDateTime=2025-10-05T09:00:00
- FILTER BY DATE RANGE: http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?startDateTime=2025-10-05T09:00:00
- FILTER BY VEHICLE MODEL: http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?model=renegade
- FILTER BY MINIMUM SEATS: http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?seats=5
- FILTER BY ALL CRITERIA COMBINED:  http://localhost:8080/api/vehicle-rental-offers/search/by-criteria?city=Valencia&st[…]:00:00&endDateTime=2025-10-31T17:00:00&model=renegade&seats=5

### Reservation (of Rental offers) 
- GET http://localhost:8080/api/reservations to get all reservations (only for ADMIN)
- GET http://localhost:8080/api/reservations/my to get own reservations
- GET http://localhost:8080/api/reservations/{id} to get reservation by ID
- POST http://localhost:8080/api/vehicle-reservations to create reservation (RENTER)
- PUT http://localhost:8080/api/reservations/{id} to update reservation date by ID
- DELETE http://localhost:8080/api/reservations/{id} to delete reservation by ID


## ✏️ ER Physical Diagram

[![temp-Imagemxv-S8-B.avif](https://i.postimg.cc/JhvdWdQt/temp-Imagemxv-S8-B.avif)](https://files.fm/u/266jy9nnvv)

## 🧪 Testing
This project includes unit tests and integration tests. 
- Unit Tests: Centered on individual components (example, service methods).
- Integration Tests: Use MockMvc to simulate HTTP requests and verify controller behaviour.

## 🔄 Workflow - Pipelines

The project uses **GitHub Actions** for continuous integration.

This project implements a robust CI/CD pipeline using GitHub Actions to automate testing, Docker image building, and release management.

### 🔧 Build Pipeline (build.yml)
[![Build Pipeline](https://github.com/More-ThanCode/GoConcoche/actions/workflows/build.yml/badge.svg)](https://github.com/More-ThanCode/GoConcoche/actions/workflows/build.yml)

**Trigger:** Automatically executes on every push to main branch

**Purpose:** Build and publish development images

**Activities:**

- Docker image building and optimization
- Pushing images to Docker Hub registry
- Tagging images with commit SHAs
- Generating build artifacts and reports

### 🎯 Release Pipeline (release.yml)
[![Release Pipeline](https://github.com/More-ThanCode/GoConcoche/actions/workflows/release.yml/badge.svg)](https://github.com/More-ThanCode/GoConcoche/actions/workflows/release.yml)

**Trigger:** Automatically activates when version tags are pushed (format: v*, e.g., v1.0.0)

**Purpose:** Production-ready deployments

### 🔍 Test Pipeline (test.yml)
[![Test Pipeline](https://github.com/More-ThanCode/GoConcoche/actions/workflows/test.yml/badge.svg)](https://github.com/More-ThanCode/GoConcoche/actions/workflows/test.yml)

**Trigger:** Automatically runs on every Pull Request targeting main

**Purpose:** Quality assurance before code merging

**Activities:**

- Runs comprehensive test suite in Docker containers
- Executes security scans and code analysis
- Generates test coverage reports
- Uses docker-compose-test.yml for test environment
- Automatic cleanup after execution

## Contributors

Morena Peralta Almada
    <a href="https://github.com/morenaperalta">
        <picture>
            <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
            <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
            <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
        </picture>
    </a>

Nia Espinal
    <a href="https://github.com/niaofnarnia">
        <picture>
            <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
            <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
            <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
        </picture>
    </a>

Sofía Santos
<a href="https://github.com/sofianutria">
    <picture>
            <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
            <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
            <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
        </picture>
    </a>

Anna Nepyivoda
    <a href="https://github.com/NepyAnna">
        <picture>
            <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
            <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
            <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
        </picture>
    </a>

Mary Kenny
<a href="https://github.com/marykenny123">
    <picture>
        <source srcset="https://img.icons8.com/ios-glyphs/30/ffffff/github.png" media="(prefers-color-scheme: dark)">
        <source srcset="https://img.icons8.com/ios-glyphs/30/000000/github.png" media="(prefers-color-scheme: light)">
        <img src="https://img.icons8.com/ios-glyphs/30/000000/github.png" alt="GitHub icon"/>
    </picture>
</a>