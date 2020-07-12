# batch-files-processor

## Prerequisites
- [JDK 14](https://www.oracle.com/java/technologies/javase-jdk14-downloads.html)

## How to starting

#### Linux/MacOS
```
./mvnw clean package -DskipTests && java -jar --enable-preview target/batch-files-processor-1.0.0.jar
```

#### Windows
```
mvnw.cmd clean package -DskipTests && java -jar --enable-preview target/batch-files-processor-1.0.0.jar
```

## How to testing

#### Linux/MacOS
```
./mvnw clean test
```

#### Windows
```
mvnw.cmd clean test
```
