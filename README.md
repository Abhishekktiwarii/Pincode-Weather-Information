
# 🌦 Pincode Weather Information API

## 📌 Overview

This project provides a **single REST API** to fetch weather information for a given:

* **Pincode**
* **Date**

The system:

1. Converts pincode → latitude & longitude (using OpenWeather Geocoding API)
2. Fetches weather data using latitude & longitude (OpenWeather Current Weather API)
3. Stores:

   * Pincode with latitude & longitude
   * Weather information per date
4. Optimizes future API calls using database caching

---

# 🚀 Features

✅ Single REST API (No UI)
✅ Testable via Postman
✅ Proper layered architecture
✅ RDBMS (H2 in-memory DB)
✅ API call optimization
✅ Pincode lat/long stored separately
✅ Weather stored per pincode & date
✅ Clean code structure

---

# 🏗 Architecture

```
Controller
    ↓
Service
    ↓
Repository
    ↓
External API Clients
```

### Components

* **WeatherController** → Handles REST API
* **WeatherService** → Business logic
* **GeocodingClient** → Calls OpenWeather Geocoding API
* **WeatherClient** → Calls OpenWeather Weather API
* **Repositories** → DB interaction (JPA)
* **H2 Database** → In-memory RDBMS

---

# 📡 API Endpoint

## GET Weather by Pincode & Date

```
GET /api/weather
```

### Query Parameters

| Parameter | Type   | Required | Example    |
| --------- | ------ | -------- | ---------- |
| pincode   | String | Yes      | 411014     |
| date      | Date   | Yes      | 2020-10-15 |

### Example Request

```
http://localhost:8080/api/weather?pincode=411014&date=2020-10-15
```

---

# 📤 Sample Response

```json
{
  "cached": false,
  "city": "Pune",
  "date": "2020-10-15",
  "description": "clear sky",
  "feelsLike": 20.9,
  "humidity": 42,
  "icon": "01n",
  "latitude": 18.5685,
  "longitude": 73.9158,
  "pincode": "411014",
  "pressure": 1015,
  "temperature": 21.59,
  "windDeg": 109,
  "windSpeed": 1.14
}
```

---

# 🧠 What Does `"cached"` Mean?

| Value | Meaning                                        |
| ----- | ---------------------------------------------- |
| false | Data fetched from external API and stored      |
| true  | Data returned from database (No API call made) |

---

# 🔄 Optimization Logic

### First Request

1. Check if weather for (pincode + date) exists in DB
2. If not:

   * Check if lat/long exists
   * If not → Call Geocoding API
   * Call Weather API
   * Save data in DB
3. Return response with `"cached": false`

---

### Second Request (Same Pincode + Date)

1. Data found in DB
2. No external API call
3. Return response with `"cached": true`

This ensures optimized API usage.

---

# 🗄 Database Tables

## 1️⃣ pincode_master

Stores:

* Pincode
* Latitude
* Longitude
* City
* Country

---

## 2️⃣ weather_cache

Stores:

* Pincode
* Weather Date
* Temperature
* Humidity
* Pressure
* Description
* Wind Info
* Created/Updated timestamps

---

# 🛠 Technologies Used

* Java 21
* Spring Boot 4
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* RestTemplate
* Lombok
* OpenWeather API

---

# ⚙️ Configuration

### application.properties

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=root

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

openweather.api.key=YOUR_API_KEY
openweather.api.geocoding-url=http://api.openweathermap.org/geo/1.0/zip
openweather.api.weather-url=https://api.openweathermap.org/data/2.5/weather
```

---

# 🧪 How To Run The Project

### 1️⃣ Clone Repository

```
git clone <repo-url>
cd pincode-weather
```

### 2️⃣ Add Your OpenWeather API Key

Replace in `application.properties`:

```
openweather.api.key=YOUR_API_KEY
```

### 3️⃣ Run Application

Using IDE or:

```
mvn spring-boot:run
```

Server runs at:

```
http://localhost:8080
```

---

# 🧪 How To Test Using Postman

### Step 1: First Call

```
GET http://localhost:8080/api/weather?pincode=411014&date=2020-10-15
```

Expected:

```
"cached": false
```

---

### Step 2: Second Call (Same Request)

```
GET http://localhost:8080/api/weather?pincode=411014&date=2020-10-15
```

Expected:

```
"cached": true
```

This proves API optimization works.

---

# 🗄 Access H2 Database

Open browser:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:mem:testdb
```

Username:

```
sa
```

Password:

```
root
```

You can inspect:

* pincode_master
* weather_cache

---

# ⚠️ Note About Historical Date

OpenWeather free API does not support historical weather data.

Therefore:

* Current weather is fetched
* Stored against requested date
* Used for optimization in future calls

This design satisfies assignment requirements while maintaining API optimization.

---

# 📈 Future Improvements

* Global exception handling
* Unit tests with Mockito
* Swagger documentation
* TTL-based cache cleanup
* Switch to PostgreSQL/MySQL for production

---

# 🎯 Assignment Compliance

| Requirement                      | Status |
| -------------------------------- | ------ |
| Single REST API                  | ✅      |
| Save pincode lat/long separately | ✅      |
| Save weather per date            | ✅      |
| Optimized API calls              | ✅      |
| RDBMS used                       | ✅      |
| Testable via Postman             | ✅      |
| Proper code structure            | ✅      |

---

# 👨‍💻 Author

Abhishek Tiwari
Software Developer

