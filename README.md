# Spring MVC Board System

Spring Framework와 Spring Security를 활용한 게시판 및 회원 관리 시스템

## 기술 스택

| 구분 | 기술 | 버전 |
|------|------|------|
| **Frontend** | JSP, JSTL | - |
| | Bootstrap | 5.x |
| | HTML/CSS/JavaScript | - |
| **Backend** | Spring Framework | 5.x |
| | Spring Security | 5.x |
| | Java | 11 |
| **Database** | MySQL | 8.0 |
| | MyBatis | 3.5 |
| **Server** | Apache Tomcat | 9.0 |
| **Build Tool** | Maven | 3.8 |
| **IDE** | IntelliJ IDEA | Ultimate |
| **OS** | macOS | - |

### Backend
- **Framework:** Spring Framework 5.x
- **Security:** Spring Security
- **ORM:** MyBatis
- **Database:** MySQL
- **Build Tool:** Maven
- **Server:** Apache Tomcat 9.0

### Frontend
- **View:** JSP, JSTL
- **CSS Framework:** Bootstrap 5
- **JavaScript:** Vanilla JS

### Tools
- **IDE:** IntelliJ IDEA Ultimate
- **Version Control:** Git

## 주요 기능

### 게시판 기능
- 게시글 CRUD (작성, 조회, 수정, 삭제)
- 파일 업로드 & 다운로드
- 이미지 썸네일 자동 생성
- 댓글 시스템 (작성, 수정, 삭제)
- 페이징 처리
- 검색 기능

### 회원 관리
- 회원가입 & 로그인 (Spring Security)
- 비밀번호 암호화 (BCrypt)
- 사용자 권한 관리 (ROLE_USER, ROLE_ADMIN)
- 회원 정보 수정
- 회원 목록 조회 (관리자)
- CSRF 공격 방어

### 보안
- Spring Security 인증/인가
- 세션 관리
- 최근 로그인 시간 기록
- 

## 📂 프로젝트 구조

```
spring_project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.koreait.www/
│   │   │       ├── config/          # Spring 설정
│   │   │       │   ├── RootConfig.java
│   │   │       │   ├── ServletConfig.java
│   │   │       │   └── SecurityConfig.java
│   │   │       ├── controller/      # 컨트롤러
│   │   │       ├── domain/          # VO (Entity)
│   │   │       ├── exception/
│   │   │       ├── handler/         # 핸들러
│   │   │       ├── repository/      # DAO (MyBatis)
│   │   │       ├── service/         # 서비스 레이어
│   │   │       └── security/        # Spring Security 설정
│   │   ├── resources/
│   │   │   └── mapper/              # MyBatis Mapper XML
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── views/           # JSP 파일
│   │       └── resources/           # 정적 리소스
│   └── test/                        # 테스트 코드
└── pom.xml                          # Maven 설정
```

## 주요 구현 내용

### 1. Spring Security 인증/인가
- BCryptPasswordEncoder를 사용한 비밀번호 암호화
- Custom AuthenticationSuccessHandler / FailureHandler
- 권한별 접근 제어 (ROLE_USER, ROLE_ADMIN)

### 2. 파일 업로드
- MultipartFile을 이용한 파일 업로드
- 이미지 썸네일 자동 생성 (Thumbnailator)
- 날짜별 폴더 자동 생성
- Apache Tika를 활용한 파일 타입 검증

### 3. MyBatis 통합
- XML Mapper 방식
- 동적 SQL (if, foreach)

### 4. 트랜잭션 관리
- @Transactional을 이용한 선언적 트랜잭션
- 파일 업로드와 DB 저장 간 트랜잭션 보장

## 구현 화면

### 메인 페이지
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 24 18" src="https://github.com/user-attachments/assets/56713ad3-eac6-45a3-9af8-3f6eeb9326dc" />

### ADMIN 로그인
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 24 36" src="https://github.com/user-attachments/assets/01ed61d4-cfc4-404b-b240-1fc8c0b57128" />

### ADMIN 회원관리
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 25 02" src="https://github.com/user-attachments/assets/7609b093-141c-4afd-9d12-fc5fa77eff47" />

### 일반회원 로그인
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 25 25" src="https://github.com/user-attachments/assets/ecb5961c-6cb4-491a-8bde-8ba71b8ffb6a" />

### 게시판
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 27 05" src="https://github.com/user-attachments/assets/d093a325-e6a3-419b-ae45-263b2f29514f" />

### 게시판 작성페이지
- writer는 수정 불가
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 28 24" src="https://github.com/user-attachments/assets/8833778e-b6a7-42e7-9de0-21e0e5abf419" />

### 게시판 상세페이지
- 본인이 작성한 게시글만 수정하기 및 삭제하기 버튼 노출
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 27 58" src="https://github.com/user-attachments/assets/7781e4b2-e3e8-4e95-aa8c-3a0fc22b5c50" />

### 게시판 수정페이지
- writer는 수정 불가
- 파일 첨부 및 삭제
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 28 57" src="https://github.com/user-attachments/assets/05ae07ce-ded3-4b23-9ea2-3cb1d4c6a835" />

### 댓글 CRUD
- more 버튼 구현
- 자신이 작성한 댓글만 수정 및 삭제 가능
- modal을 통한 수정

<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 33 27" src="https://github.com/user-attachments/assets/f8024c0f-07e6-4597-b72f-c2f40c098ea5" />
<img width="1624" height="1060" alt="스크린샷 2025-12-16 오전 10 33 59" src="https://github.com/user-attachments/assets/82d821a0-42b3-41e9-8c45-1c9b7b5ada71" />


## 🔧 환경 설정

### application.properties 예시
```properties
# Database
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/springdb
spring.datasource.username=root
spring.datasource.password=your_password

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=50MB
```

## 개발 일지

### 2024.11 ~ 2024.12
- Spring Framework 설정 및 기본 구조 구축
- 게시판 CRUD 기능 구현
- 파일 업로드 시스템 구현

### 2024.12
- Spring Security 통합
- 회원 관리 시스템 구현
- 댓글 기능 추가
- UI/UX 개선


## 개발자
- **Name:** HSM
- **GitHub:** [@hssam99](https://github.com/hssam99)


---
