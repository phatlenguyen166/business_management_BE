# Dockerfile cho backend Spring Boot

# STAGE 1: Build với Maven
FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app

# Sao chép file pom.xml trước để tận dụng cache khi tải thư viện
COPY pom.xml .
# Tải các phụ thuộc Maven (trong layer riêng để tận dụng cache)
RUN mvn dependency:go-offline -B

# Copy mã nguồn
COPY src ./src
COPY fonts ./fonts

# Build ứng dụng
RUN mvn package -DskipTests

# STAGE 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Tạo các thư mục cần thiết
RUN mkdir -p /app/payroll /app/reports

# Copy các tài nguyên cần thiết
COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/fonts ./fonts

# Biến môi trường
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS=""

# Expose port
EXPOSE 8080

# Volume cho dữ liệu
VOLUME ["/app/payroll", "/app/reports"]

# Entry point
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]