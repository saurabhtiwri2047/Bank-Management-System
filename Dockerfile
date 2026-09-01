FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN apt-get update && \
    apt-get install -y ant && \
    rm -rf /var/lib/apt/lists/*

RUN ant clean jar

CMD ["sh", "-c", "java -jar dist/*.jar"]
