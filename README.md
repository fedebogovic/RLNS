# Rate-Limited Notification Service

This project implements a rate-limited notification service in Java. The service ensures that recipients do not receive too many notifications within a specified timeframe, protecting against system errors and abuse.

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven

### Running the Application

1. Clone the repository:

   ```bash
   git clone https://github.com/fedebogovic/RLNS.git
   cd rate-limited-notification-service
   ```

2. Compile and run the main application:

   ```bash
   mvn compile exec:java -Dexec.mainClass="com.example.RateLimitedNotificationService"
   ```

### Running the Tests

1. To run the unit tests, use the following Maven command:

   ```bash
   mvn test
   ```

### Project Structure

```
modak-challenge/
├── pom.xml
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── example
    │               ├── NotificationService.java
    │               ├── NotificationServiceImpl.java
    │               ├── Gateway.java
    │               ├── RateLimiter.java
    │               └── RateLimitedNotificationService.java
    └── test
        └── java
            └── com
                └── example
                    └── NotificationServiceImplTest.java
```

### Customizing Rate Limiters

You can customize the rate limiters by modifying the `defaultRateLimiters` method in `NotificationServiceImpl` 

```java
public static Map<String, RateLimiter> defaultRateLimiters() {
        Map<String, RateLimiter> rateLimiters = new HashMap<>();
        rateLimiters.put("status", new RateLimiter(2, Duration.ofMinutes(1)));
        rateLimiters.put("news", new RateLimiter(1, Duration.ofDays(1)));
        rateLimiters.put("marketing", new RateLimiter(3, Duration.ofHours(1)));
        return rateLimiters;
}
```

Or by passing a custom map of rate limiters to the constructor.

```java
Map<String, RateLimiter> customRateLimiters = new HashMap<>();
customRateLimiters.put("customType", new RateLimiter(1, Duration.ofMinutes(1)));
NotificationServiceImpl service = new NotificationServiceImpl(new Gateway(), customRateLimiters);
```
