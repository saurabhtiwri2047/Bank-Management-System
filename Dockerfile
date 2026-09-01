FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install -y ant

RUN ant clean jar

CMD ["sh", "-c", "java -jar dist/*.jar"]
