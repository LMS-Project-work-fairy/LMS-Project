<div align="center">

# LMS Project
### Learning Management System

분산된 학사 서비스를 하나의 흐름으로 통합해  
학생과 교수가 하나의 시스템 안에서 학습 행정과 커뮤니케이션을 처리할 수 있도록 설계한 콘솔 기반 LMS 프로젝트입니다.

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-Build-02303A?style=for-the-badge&logo=gradle)
![MySQL](https://img.shields.io/badge/MySQL-DB-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-Collaboration-181717?style=for-the-badge&logo=github)

> One-Stop 통합 플랫폼 구축을 목표로, 로그인/회원가입, 수강신청, 강좌 관리, 과제·성적, 메시지 기능을 하나의 학사 워크플로우로 연결한 프로젝트

</div>

---

## 1. Project Overview

기존 학사 환경은 메신저 시스템, 수강신청 시스템, 과제 제출 시스템이 분산되어 있어 사용자 경험이 단절되는 문제가 있었습니다.  
본 프로젝트는 이러한 분산 구조를 하나로 통합하여, **이용자 편의성과 관리 효율을 높이는 LMS**를 구현하는 것을 목표로 합니다.

### 핵심 목표
- 분산된 학사 서비스를 하나의 플랫폼으로 통합
- 학생/교수 역할에 맞는 기능 분리 및 흐름 설계
- 로그인 보안 정책과 상태 관리 기능 구현
- 과제, 성적, 메시지를 포함한 실제 학사 흐름의 연결
- GitHub 기반 협업 프로세스 경험 축적

---

## 2. Why This Project

이 프로젝트는 단순히 기능을 나열한 CRUD 과제가 아니라,

- **학생의 수강신청 → 교수 문의 → 정원 조정**
- **과제 등록 → 제출 → 성적 확인 → 이의 제기 → 답변**

처럼 실제 학사 업무 흐름이 하나의 시스템 안에서 이어지도록 설계한 것이 특징입니다.

즉, 개별 기능 구현보다 **“통합 학사 워크플로우”** 자체를 구현하는 데 초점을 맞췄습니다.

---

## 3. Core Features

### 3-1. 통합 로그인 / 회원가입
- 학생 로그인 / 교수 로그인 유형 분리
- 아이디·비밀번호 불일치 시 실패 횟수 누적
- 3회 이상 실패 시 로봇 확인 단계 추가
- 5회 이상 실패 시 계정 잠금 정책 적용
- 학생/교수별 회원가입 제약 조건 분리
- 형식 오류, 중복 입력, 비밀번호 정책 검증, 뒤로가기 기능 제공

### 3-2. 학생 기능
- 수강 가능한 강의 목록 조회
- 수강 신청 / 취소
- 학점 제한, 수강 인원 제한, 중복 시간표 검증
- 수강 강의별 과제 및 성적 조회
- 전체 평균 평점 확인
- 메시지함 확인, 답장, 대화방 기반 메시지 송수신
- 개인정보 조회 및 수정

### 3-3. 교수 기능
- 신규 강좌 등록 및 삭제
- 담당 과목 조회
- 수강 학생 명단 조회
- 학생 성적 수정
- 강좌 정보 수정
- 과제 등록
- 채팅방 기반 메시지 기능
- 개인정보 수정

### 3-4. 메시지 기능
- 받은 메시지 확인 및 답장
- 내게 온 메시지와 발신자 정보 조회
- 답장 여부에 따른 읽음/답장 필요 상태 반영
- 실제 대화 UI와 유사한 채팅방 흐름 구현
- 학생과 교수 간 학사 문의를 시스템 내부에서 처리 가능

---

## 4. Security & Validation

본 프로젝트는 콘솔 기반 애플리케이션이지만, 서비스 진입 안정성을 높이기 위해 로그인 보안 흐름을 별도로 설계했습니다.

### 로그인 보안 정책
- 로그인 실패 횟수 누적 표시
- 3회 실패 시 로봇 확인 단계 추가
- 5회 실패부터 계정 잠금
- 학생/교수별 잠금 시간 차등 적용

### 잠금 단계
| 단계 | 학생 | 교수 |
|---|---|---|
| 1단계 | 5회 실패 -> 3분 잠금 | 5회 실패 -> 5분 잠금 |
| 2단계 | 10회 실패 -> 5분 잠금 | 10회 실패 -> 10분 잠금 |
| 3단계 | 15회 실패 -> 10분 잠금 | 15회 이상 실패 -> 30분 잠금 |
| 4단계 | 20회 이상 실패 -> 30분씩 잠금 | 20회 이상 실패 -> 30분씩 잠금 |

### 회원가입 검증 포인트
- 학생: 학번 8자리 검증
- 교수: 교수 번호 형식 검증 및 교수 인증 코드 필요
- 이메일 형식 검증
- 주민등록번호 / 전화번호 자릿수 검증
- 비밀번호 8자리 이상, 숫자/특수문자 포함
- 중복 정보 재입력 요구
- 입력 단계별 오류 피드백 제공

---

## 5. Service Flow

### 시나리오 1. 수강 문의와 정원 조정
1. 학생이 회원가입 후 수강신청 시도
2. 정원 초과로 신청 불가
3. 학생이 메시지 기능으로 담당 교수에게 수강 요청 전달
4. 교수가 메시지 확인 후 답장
5. 교수의 강의 관리 기능으로 수용 인원 조정
6. 학생이 동일 시스템 내에서 문제 해결

### 시나리오 2. 과제 제출과 성적 이의 제기
1. 교수가 과제 등록
2. 학생이 과제 조회 후 제출
3. 교수가 평가 후 성적 입력
4. 학생이 성적 조회
5. 학생이 메시지 기능으로 정정 요청 전달
6. 교수가 검토 후 답변 및 처리

이처럼 본 프로젝트는 **문의, 응답, 수정, 반영**까지 이어지는 학사 운영 흐름을 하나의 시스템 안에서 연결합니다.

---

## 6. Architecture

프로젝트는 계층별 책임을 분리하여 구성했습니다.

### 계층 구조
- **Application**: 시스템 실행 및 초기 조립, 진입점
- **View + Controller**: 메뉴 출력, 사용자 입력 수집, 요청 해석 및 기능 분기
- **Service + DTO**: 인증/수강/강의 관련 핵심 비즈니스 로직 처리 및 계층 간 데이터 전달
- **DAO / DB**: 데이터 접근 및 SQL 처리

### Repository Structure
```bash
src/main/java
└─ com.lms
   ├─ application
   ├─ common
   ├─ controller
   ├─ model
   │  ├─ dao
   │  ├─ dto
   │  └─ service
   ├─ query
   └─ view

src/main/resources
├─ db.properties
└─ db.properties.example

database
├─ 01_create_database.sql
├─ 02_create_tables.sql
├─ 03_constraints.sql
├─ 04_dummy_data.sql
└─ 05_test_query.sql
```

---

## 7. Tech Stack

### Language / Runtime
- Java

### Build / Dependency
- Gradle

### Database / Connection
- MySQL Connector/J
- HikariCP

### Console Interaction
- JLine

### Collaboration
- Git
- GitHub
- Issues / Pull Requests / Discussions

---

## 8. Technical Challenges & Solutions

### 8-1. 로그인 보안 상태 초기화 문제
**문제**  
로그인 실패 횟수 제한과 잠금 기능은 적용되어 있었지만, 로그인 성공 후에도 이전 실패 기록이 남아 있어 정상 사용자도 다음 로그인에서 불필요한 보안 단계를 거치는 문제가 있었습니다.

**해결**  
`recordLoginSuccess()`를 추가해 로그인 성공 시 실패 기록과 보안 상태를 초기화하도록 개선했습니다.

**배운 점**  
인증 기능은 단순 검증 로직만이 아니라, 성공/실패 이후의 **상태 전이 설계**까지 포함해야 안정적으로 동작한다는 점을 학습했습니다.

### 8-2. 교수·학생 메시지 포맷 호환 문제
**문제**  
학생 메시지 기능은 날짜와 시간을 포함한 포맷을 기준으로 구성되어 있었지만, 교수 메시지 기능은 동일한 형식을 따르지 않아 메시지 조회 시 호환 문제가 발생했습니다.

**해결**  
교수 메시지에도 학생과 동일한 발신일 포맷과 파싱 방식을 적용하여 메시지 구조를 통일했습니다.

**배운 점**  
공통 기능은 역할별로 개별 구현하는 것보다, 처음부터 **공통 규격을 표준화**하는 것이 유지보수성과 확장성 측면에서 더 중요하다는 점을 확인했습니다.

---

## 9. Team & Roles

| 이름 | 역할 | 담당 내용 |
|---|---|---|
| 김용준 | Login / PM | 로그인 및 보안 흐름, 실패 횟수 제한, 로봇 확인, 시간 제한, GitHub 협업 운영 기준 정리 |
| 이강욱 | Student Sign Up | 학생 회원가입 정보 통일, 입력 오류 피드백, 뒤로가기, 중복 확인 등 회원가입 UX 개선 |
| 전지원 | Professor Sign Up | 교수 정보 통일, 교수 인증 코드 기반 가입 보안, 입력 검증 및 중복 처리 개선 |
| 서정림 | Student Features | 수강 신청/취소, 과제/성적 조회, 메시지, 정보 수정, 학점 제한/중복 시간표/답장 상태 등 학생 기능 구현 |
| 박정민 | Professor Features | 강좌 등록/삭제, 담당 과목 조회, 성적 관리, 과제 등록, 채팅방 및 교수 메인 메뉴 기능 구현 |

---

## 10. Collaboration Workflow

본 프로젝트는 GitHub 협업 전략을 중심으로 운영했습니다.

### 브랜치 전략
- 각 팀원은 기능별 브랜치에서 작업
- 원격 저장소에 주기적으로 push
- `develop` 브랜치에서 기능 병합
- 최종 검수 후 `master` 브랜치로 병합

### 협업 흐름
```text
Issue -> Branch -> Commit -> Pull Request -> Review -> Merge
```

### 운영 방식
- 기능 개선 및 추가 아이디어를 Issue로 관리
- Daily Task를 통해 당일 역할 분담과 작업 내용 공유
- 회의록과 협업 규칙을 GitHub Discussion으로 문서화

---

## 11. Expected Improvements

프로젝트 발표 기준으로 도출한 향후 확장 방향은 다음과 같습니다.

### 1) 통합 학사 워크플로우 고도화
문의, 승인, 정원 조정, 수강 반영까지 실제 학사 절차를 더 자동화된 흐름으로 확장

### 2) 보안·상태관리 체계 강화
세션 관리, 권한 분리, 비밀번호 재설정 등 보다 체계적인 인증/보안 구조로 발전

### 3) 규격 표준화 및 유지보수성 개선
메시지 포맷, 검증 로직, 공통 메서드 구조를 통일해 재사용성과 유지보수성을 향상

### 4) UX 기반 운영 플랫폼 확장
알림, 마감 리마인드, 성적 이의신청, 관리자 기능 등을 추가해 실무형 LMS로 확장

---

## 12. How to Run

> 아래 실행 절차는 현재 저장소 구조를 기준으로 정리한 기본 가이드입니다.  
> 실제 환경에 따라 DB 계정 정보와 실행 클래스는 보완이 필요할 수 있습니다.

### 1. 저장소 클론
```bash
git clone https://github.com/LMS-Project-work-fairy/LMS-Project.git
cd LMS-Project
```

### 2. 데이터베이스 준비
`database` 폴더 내 SQL 파일을 순서대로 실행합니다.

```text
01_create_database.sql
02_create_tables.sql
03_constraints.sql
04_dummy_data.sql
05_test_query.sql
```

### 3. DB 설정 파일 작성
`src/main/resources/db.properties.example`를 참고해  
`src/main/resources/db.properties` 파일에 DB 연결 정보를 입력합니다.

### 4. 애플리케이션 실행
Gradle 또는 IDE(IntelliJ 등)에서 메인 애플리케이션을 실행합니다.

```bash
./gradlew build
```

---

## 13. Screenshots

> 아래 영역은 GitHub README 최종 업로드 시 실제 이미지로 교체하면 좋습니다.

### 로그인 / 보안 흐름
```markdown
![login-security](./docs/images/login-security.png)
```

### 학생 기능
```markdown
![student-features](./docs/images/student-features.png)
```

### 교수 기능
```markdown
![professor-features](./docs/images/professor-features.png)
```

### 메시지 / 채팅방
```markdown
![message-chat](./docs/images/message-chat.png)
```

---

## 14. Project Value

이 프로젝트는 콘솔 기반 시스템이지만,

- 로그인 보안 정책 설계
- 학생/교수 역할 분리
- 메시지 기반 학사 커뮤니케이션
- 수강, 과제, 성적의 흐름 연결
- GitHub 기반 협업 운영 경험

을 함께 담고 있다는 점에서, 단순 기능 구현을 넘어 **실제 서비스 운영 흐름을 구조적으로 고민한 팀 프로젝트**라는 의미를 가집니다.

---

## 15. License

This project was created for educational purposes.
