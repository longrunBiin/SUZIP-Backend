# SUZIP-Backend

사용 언어 : JAVA

프레임워크 : Spring Boot3, Jpa / Spring Data Jpa

개발 환경 : Amazon EC2, Amazon RDS, Amazon S3

데이터베이스 : MySql

---
프로젝트 구조
![image](https://github.com/user-attachments/assets/323dc90a-4e11-489d-9db0-158463a35d1d)

개선 사항
- 인덱스를 사용한 검색 성능 최적화

다음은 MemberRepository의 `findMemberByUserName` 메소드에 인덱스를 적용한 결과에 대한 요약입니다.

- **성능 개선 사례: `findMemberByUserName` 메소드 최적화**
    - **배경**
        - 회원 데이터 조회 메소드인 `findMemberByUserName`는 대부분의 기능에서 사용되었지만, 인덱스가 없어서 성능 저하가 우려됨.
        - 회원가입 시 `user_name` 필드는 삽입/변경이 드물어 인덱스 추가에 적합.

### 성능 테스트

### 데이터 10,000개일 때

- **인덱스 없음**: 실행 시간 92ms
- **인덱스 있음**: 실행 시간 78ms
    - 인덱스를 적용했을 때 약 15%의 성능 향상이 있었습니다.

### 데이터 100,000개일 때

- **인덱스 없음**: 실행 시간 128ms
- **인덱스 있음**: 실행 시간 78ms
    - 인덱스 적용 시 약 39%의 성능 향상이 나타났습니다.

### 데이터 1,000,000개일 때

- **인덱스 없음**: 실행 시간 551ms
- **인덱스 있음**: 실행 시간 83ms
    - 인덱스 적용으로 약 85%의 성능 향상이 있었습니다.
 
### 결과
    - 데이터 10,000개에서 1,000,000개까지 테스트한 결과, 인덱스를 적용했을 때 쿼리 실행 속도가 최대 85% 향상됨.
    - 최적화 적용 후 더 많은 사용자가 동시 접속해도 안정적인 응답 속도 유지 가능.


- 사용자 로그인 및 인증 방식 리팩토링
