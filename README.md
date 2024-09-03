# SUZIP-Backend

사용 언어 : JAVA

프레임워크 : Spring Boot3, Jpa / Spring Data Jpa

개발 환경 : Amazon EC2, Amazon RDS, Amazon S3

데이터베이스 : MySql

---
프로젝트 구조
![image](https://github.com/user-attachments/assets/323dc90a-4e11-489d-9db0-158463a35d1d)

---

개선해야할 점
일기 저장시 분석 성능 최적화
DB 인덱싱, 캐싱?
redis 적용하여 refresh token 관리


만약 10000명이 동시에 일기 저장을 할때 동시성 제어 고민
