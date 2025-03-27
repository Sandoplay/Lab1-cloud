# --- Stage 1: Build ---
# Використовуємо базовий образ з JDK 17 (відповідно до твого проекту) та Maven
FROM maven:3.8.5-openjdk-17 AS builder

# Встановлюємо робочу директорію всередині образу
WORKDIR /app

# Копіюємо pom.xml для завантаження залежностей (використовує кеш Docker)
COPY pom.xml .

# Копіюємо решту коду проекту
COPY src ./src

# Збираємо JAR-файл, пропускаючи тести
# Команда '-Dmaven.test.skip=true' пропускає компіляцію та виконання тестів
RUN mvn package -Dmaven.test.skip=true -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

# --- Stage 2: Runtime ---
# Використовуємо менший базовий образ лише з JRE 17 для запуску
FROM eclipse-temurin:17-jre-jammy

# Встановлюємо робочу директорію
WORKDIR /app

# Копіюємо зібраний JAR-файл з першого етапу (builder)
COPY --from=builder /app/target/Lab1-cloud-0.0.1-SNAPSHOT.jar app.jar

# Вказуємо порт, який слухає Spring Boot (за замовчуванням 8080)
EXPOSE 8080

# Команда для запуску додатку при старті контейнера
ENTRYPOINT ["java", "-jar", "app.jar"]