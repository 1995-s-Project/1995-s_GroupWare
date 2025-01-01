<h1>🐶PetHarmony🐶</h1>
<img src="https://github.com/user-attachments/assets/fcd3dcec-cf8f-449b-afa5-ff8bb94f6364">

## 🗒️목차

1. [프로젝트 소개](#프로젝트-소개)
2. [팀원 소개](#팀원-소개)
3. [개발환경](#개발환경)
4. [프로젝트 기능 소개](#프로젝트-기능-소개)
5. [ERD 설계](#erd-설계) 

## 🔥프로젝트 소개
개요
- PetHarmony 프로젝트는 구조 동물 보호소를 위한 그룹웨어입니다.
- 이 프로젝트는 보호소의 구조 동물 상태와 입양 절차에 대한 정보를 제공하여 운영 효율성을 증대시킵니다.
  
<br>

주요 기능
- 반복적인 업무를 최소화 하여 보호소 직원들이 동물 복지 및 입양 상담과 같은 중요한 일에 집중할 수 있도록 지원합니다.
- 데이터 시각화를 통해 입양 트렌드를 파악하고 마케팅 전략을 수립할 수 있습니다.
  
<br>

특징
- 한국의 보호소 환경에 특화된 그룹웨어로, 현재 시장에 유사한 솔루션이 없어 큰 잠재력을 가지고 있는 것이 특징입니다.

<br>

개발기간
| 구분                          | 기간                          |
|-------------------------------|-------------------------------|
| 전체 개발 기간                | 2024년 11월 18일 ~ 2024년 12월 30일 |
| 프로젝트 기획 및 프로토타입 제작, DB설계 | 2024년 11월 18일 ~ 2024년 12월 06일 |
| Front-End 및 Back-End 기능 개발 | 2024년 12월 07일 ~ 2024년 12월 30일 |


## 😊팀원 소개

| 이름       | 이미지          | 역할               | 기능개발               |
|------------|-----------------|--------------------|--------------------|
| 김경훈     | <img src="https://github.com/user-attachments/assets/dcd31cb3-f7cd-4aa0-bef2-9111a28701d3" alt="경훈님" width="100" /> | 프로젝트 매니저    | 메신저(채팅), PPT  |
| 정근희     | <img src="https://github.com/user-attachments/assets/f63fb788-dbc7-485c-a643-0971e19dd89e" alt="근희님" width="100" /> | 형상관리자             | 로그인, 사원번호 찾기, 비밀번호 찾기, 메인화면, 근태관리, 관리자페이지, 결재  |
| 김관훈     | <img src="https://github.com/user-attachments/assets/a09c05f1-51fa-467a-bc56-b826e6307f2d" alt="관훈님" width="100" /> | DBA           | 쪽지, 전사게시판, 직원정보 |
| 정은미     | <img src="https://github.com/user-attachments/assets/e4536a2d-c9dc-427d-b027-fbab32c02947" alt="은미님" width="100" /> | DBA          | 입양, 구조동물, 재고관리, 마이페이지(재민님과 협업)          | 
| 박재민     | <img src="https://github.com/user-attachments/assets/51b820d3-bb25-4e51-961c-2ce056e9a8c6" alt="재민님" width="100" height="100" /> | DBA          | 마이페이지(은미님과 협업)          |



## 🛠️개발환경
| 🖥️Front-End                                   |
|---------------------------------------------|
| HTML, CSS, JavaScript       |
| Thymeleaf, Chart.js, Full-Calendar |

| 🔙Back-End    |
|-------------|
| Java17             |
| SpringBoot, SpringWeb, SpringSecurity|
| Spring WebSocket, Spring Mail, Lombok|

| 📁DBMS 및 ORM                                |
|---------------------------------------------|
| MySQL, MyBatis       |



## 📌프로젝트 기능 소개
### 홈
<img src="https://github.com/user-attachments/assets/91350494-65a9-4be7-9f6b-3713bed4170f"> <br>
로그인
- 관리자에게 부여받은 사원번호로 로그인할 수 있습니다.
- 사번 저장을 체크하고 로그인할 시 가장 최근에 로그인했던 사번으로 저장이 됩니다.

<br>

<img src="https://github.com/user-attachments/assets/a6799421-94ec-4def-a5ec-ef47f7468149"> <br>
사원번호 찾기
- 사원번호가 기억이 나지 않는다면 사원번호 찾기를 통해 확인할 수 있습니다.
- 본인이 속한 부서명, 이름, 이메일이 모두 DB와 동일할 경우에만 <br>
  사원번호를 확인할 수 있으며 하나라도 일치하지 않을 시 알림창으로 안내가 됩니다.
- 하나라도 입력을 안할 시 제출할 수 없도록 하였습니다

<br>

<img src="https://github.com/user-attachments/assets/4df9f192-e05c-4972-a586-dbae250bf248"> <br>
비밀번호 찾기
- 만약 비밀번호를 잊어버렸다면 비밀번호 찾기를 통해 임시 비밀번호를 발급받을 수 있습니다.
- 사번과 이메일이 DB와 일치하지 않는다면 임시 비밀번호가 발송되지 않으며 일치할 경우에만 발송이 됩니다. 

<br>

## ✏️ERD 설계
테스트


