# Usar una imagen base de OpenJDK 17 con Alpine Linux
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado al directorio de trabajo del contenedor
COPY target/*.jar app.jar

# Exponer el puerto en el que se ejecuta la aplicación (por ejemplo, 8080)
EXPOSE 8080

# Comando por defecto para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]