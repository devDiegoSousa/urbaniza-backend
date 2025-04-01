# 1. Escolhe uma imagem base do OpenJDK
FROM eclipse-temurin:17-jdk-alpine

# 2. Define o diretório de trabalho dentro do container
WORKDIR /app

# 3. Copia o JAR do projeto para o container
COPY target/*.jar app.jar

# 4. Define o comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
