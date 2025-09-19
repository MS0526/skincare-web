🌿 스킨케어 맞춤 루틴 & 성분 분석 웹 애플리케이션
매일 사용하는 화장품, 내 피부에 정말 맞을까요? 이 프로젝트는 사용자가 자신의 스킨케어 루틴을 체계적으로 관리하고, 화장품 성분을 손쉽게 분석하여 더 건강한 피부 관리를 할 수 있도록 돕는 웹 애플리케이션입니다.

✨ 주요 기능

춤 제품 추천: 진단된 피부 타입에 따라 적합한 제품 목록을 추천합니다.

회원가입 및 로그인: 안전한 비밀번호 암호화를 통해 사용자 정보를 관리합니다.

나만의 스킨케어 루틴 관리: 아침(Morning)과 저녁(Evening)으로 나누어 자신만의 스킨케어 루틴을 기록하고 관리할 수 있습니다.

텍스트 기반 성분 검색: 궁금한 화장품 성분을 이름으로 직접 검색하여 효능, 위험도 등 상세 정보를 확인할 수 있습니다.

이미지(OCR) 기반 성분 분석: 스마트폰으로 촬영한 화장품 성분표 이미지를 업로드하면, OCR 기술을 통해 텍스트를 자동으로 추출하고 DB에 저장된 성분과 매칭하여 분석 결과를 보여줍니다.

관리자 기능: 관리자(Admin)는 모든 사용자 목록을 조회하고 관리할 수 있습니다.

🛠️ 기술 스택
구분

기술

Backend

Java 17, Spring Boot, Spring Security

Database

MySQL, JPA / Hibernate

Frontend

HTML, CSS, JavaScript, Thymeleaf

Build Tool

Gradle (또는 Maven)

Libraries

Lombok, Tess4J (for OCR)

🚀 시작하기
이 프로젝트를 로컬 환경에서 실행하는 방법은 다음과 같습니다.

1. 사전 준비 사항
Java 17 (JDK)

IDE (IntelliJ, VSCode 등)

MySQL 데이터베이스 서버

2. 프로젝트 클론
git clone [https://github.com/](https://github.com/)[YOUR_GITHUB_ID]/[YOUR_REPOSITORY_NAME].git
cd [YOUR_REPOSITORY_NAME]

3. 데이터베이스 설정
MySQL에 접속하여 이 프로젝트가 사용할 데이터베이스를 생성합니다. (예: skincaredb)

CREATE DATABASE skincaredb CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

테이블은 애플리케이션 실행 시 JPA(ddl-auto)가 자동으로 생성합니다.

4. 환경 변수 설정
src/main/resources/ 경로에 application.properties 파일을 생성하고 아래 내용을 참고하여 자신의 환경에 맞게 작성합니다. 보안을 위해 실제 비밀번호 등은 환경변수로 설정하는 것을 권장합니다.

# 서버 포트 설정
server.port=8080

# 데이터베이스 연결 정보
spring.datasource.url=jdbc:mysql://localhost:3306/skincaredb
spring.datasource.username=root
# [중요] 실제 DB 비밀번호를 환경변수로 설정하세요.
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA 설정 (개발 시에는 'update' 사용, 배포 시에는 'validate' 또는 'none' 권장)
spring.jpa.hibernate.ddl-auto=update

# OCR 데이터 경로 설정 (Tess4J)
# [중요] 'tessdata' 폴더가 위치한 실제 경로를 입력하세요.
tess4j.datapath=C:/path/to/your/tessdata

[실행 전] 터미널에서 DB_PASSWORD 환경변수를 설정해야 합니다.

Windows (CMD): set DB_PASSWORD=your_password

Windows (PowerShell): $env:DB_PASSWORD="your_password"

macOS / Linux: export DB_PASSWORD="your_password"

5. 애플리케이션 실행
터미널에서 아래 명령어를 실행하거나, IDE의 실행 버튼을 클릭합니다.

./gradlew bootRun
# 또는 Maven인 경우
# ./mvnw spring-boot:run

실행 후 웹 브라우저에서 http://localhost:8080으로 접속하여 확인합니다.


