

# URL-Shortener

## 실행방법

1. 자바 설치: `sudo apt-get install openjdk-8-jdk` (안되면 `sudo apt-get update` 후 실행)
2. redis 설치: `sudo apt-get install redis-server` (안되면 `sudo apt-get update` 후 실행)
3. git 설치
   1. 패키지 리스트 업데이트: `sudo apt-get install git`
   2. git 설치:  `sudo apt install git`
4. `git clone https://github.com/kimevanjunseok/URL-Shortener.git`
5. 이동: `cd URL-Shortener`
6. build: `./gradlew build`  (현재 위치 URL-Shortener)
7. 서버 실행: `java -jar build/libs/shortener-0.0.1-SNAPSHOT.jar`