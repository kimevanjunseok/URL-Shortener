

# URL-Shortener

## 실행방법

1. 자바 설치: `sudo apt-get install openjdk-8-jdk` (안되면 `sudo apt-get update` 후 실행)
2. redis 설치: `sudo apt-get install redis-server` (안되면 `sudo apt-get update` 후 실행)
3. 현재 위치(`/URL-Shortener`)
4. build: `./gradlew build` 
5. 서버 실행: `java -jar build/libs/shortener-0.0.1-SNAPSHOT.jar`