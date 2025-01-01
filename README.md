<h1>🐶PetHarmony🐶</h1>
<img src="https://github.com/user-attachments/assets/fcd3dcec-cf8f-449b-afa5-ff8bb94f6364">

## 🗒️목차

1. [프로젝트 소개](#프로젝트-소개)
2. [팀원 소개](#팀원-소개)
3. [개발환경](#개발환경)
4. [프로젝트 기능 소개](#프로젝트-기능-소개)
5. [ERD 및 피그마 설계](#erd-및-피그마-설계계) 

<br>

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

<br>

## 😊팀원 소개

| 이름     | 이미지                                                                 | 역할     | 기능개발                                                        |
|----------|-----------------------------------------------------------------------|----------|-----------------------------------------------------------------|
| 김경훈   | <img src="https://github.com/user-attachments/assets/dcd31cb3-f7cd-4aa0-bef2-9111a28701d3" width="130"> | PM       | 메신저(채팅), PPT                                              |
| 정근희   | <img src="https://github.com/user-attachments/assets/f63fb788-dbc7-485c-a643-0971e19dd89e" width="130"> | 형상관리자 | 로그인, 사번&비밀번호 찾기, 메인화면, 결재, 관리자 |
| 김관훈   | <img src="https://github.com/user-attachments/assets/a09c05f1-51fa-467a-bc56-b826e6307f2d" width="130"> | DBA      | 쪽지, 전사게시판, 직원정보                                     |
| 정은미   | <img src="https://github.com/user-attachments/assets/e4536a2d-c9dc-427d-b027-fbab32c02947" width="130"> | DBA      | 입양, 구조동물, 재고관리, 마이페이지(재민님과 협업)           |
| 박재민   | <img src="https://github.com/user-attachments/assets/51b820d3-bb25-4e51-961c-2ce056e9a8c6" width="130" height="130"> | DBA      | 마이페이지(은미님과 협업)                                     |



<br>

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

<br>

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

### 메인 페이지
<img src="https://github.com/user-attachments/assets/e1e97260-7905-4009-a42b-1a141e1817a0"> <img src="https://github.com/user-attachments/assets/0a4cdb49-8482-49bb-aeae-47e2fda5810e"> <br>
메인 페이지
- 상단 바는 전사 게시판에 최신 글부터 확인할 수 있도록 이벤트를 주었으며 클릭하면 해당 게시글로 이동 됩니다.
- Chart.js 라이브러리 및 JavaScript를 DB와 연결 하여 현재 입양 현황과 보유중인 품종을 실시간으로 확인이 가능합니다.
- 마이페이지 버튼을 누르면 바로 마이페이지로 이동할 수 있습니다.
- 권한에 따라 관리자 전용 메뉴 버튼을 보이게 혹은 숨기도록 하였습니다.

<br>

### 근태관리
<img src="https://github.com/user-attachments/assets/16bce29f-6b84-4509-bd17-1914ad7f0984"> <br>
근태관리
- 로그인 후 출근 및 퇴근버튼을 누르면 근태관리 캘린더 날짜에 근태가 기록됩니다.
  - 퇴근버튼은 출근버튼을 먼저 누르지 않으면 기록될 수 없도록 하였습니다. 
- 현재 나의 연차 및 휴가 갯수를 확인할 수 있습니다.

<br>

<img src="https://github.com/user-attachments/assets/57c585fb-57f6-4c62-b091-1139ab1d960c"> <br>
근태수정
- 만약 근태 기록이 잘못되었다면 근태수정을 요청할 수 있습니다.
- 요청날짜를 선택 후 출근수정 또는 퇴근수정을 누르면 해당 날짜의 맞는 출퇴근 기록이 자동으로 가져옵니다.
- 이 후에 수정할 시간대를 입력한 뒤 상급자를 선택한 다음 근태수정 사유를 입력하고 나서 제출할 수 있도록 하였습니다.

<br>

### 메신저(채팅)
<img src="https://github.com/user-attachments/assets/277ce8af-1812-4268-82d8-bb8de453bf15"> <br>
메신저(채팅)
- 메신저를 통해 상급자에게 채팅을 전송할 수 있습니다.
- 부서, 직급, 이름을 통해 필터링이 가능 합니다.
- 읽지 않은 채팅 수 만큼 빨간색 동그라미로 확인이 가능합니다.

<br>

### 쪽지
<img src="https://github.com/user-attachments/assets/898d12d2-2d37-4e84-9535-1513fdcc4913"> <br>
쪽지
- 사내 직원에게 쪽지를 전송할 수 있습니다.
- 개인 및 단체로 전송이 가능하도록 하였으며 각각의 쪽지함마다 보관 또는 삭제할 수 있습니다.

<br>

### 입양관리
<img src="https://github.com/user-attachments/assets/71936b23-34f6-4ede-a8c0-d0e2c13609ca"> <br>
입양관리
- 보호소에서 관리중인 유기동물 데이터 기반으로 입양 절차에 대해 관리할 수 있도록 하였습니다.
- 구조동물 탭에서 등록 되있는 데이터로 입양 등록을 할 수 있습니다.
- 입양이 완료되면 입양완료 버튼을 눌러서 입양완료 탭에 정리 될 수 있도록 하였습니다.
- 입양 후 파양이 될 수 있는 상황도 있을 수 있기 때문에 만약 파양될 시 해당 동물은 파양 표시가 되어 별도로 확인이 가능하도록 하였습니다. 

<br>

### 구조동물
<img src="https://github.com/user-attachments/assets/7feeb064-96e1-4b68-a385-314679beea2b"> <br>
구조동물
- 구조된 유기동물들을 등록할 수 있습니다.
- 등록된 동물들은 입양관리 탭과 데이터가 공유 됩니다.
- 개 또는 고양이, 각각의 품종, 성별, 구조된 날짜로 필터링이 가능하도록 하였습니다

<br>

### 재고관리
<img src="https://github.com/user-attachments/assets/6d8f087c-c7a5-45d9-8d22-0b10a4318761"> <br>
재고관리
- 사내에 재고 현황을 확인하면서 재고수량을 업데이트 할 수 있습니다.
- 재고가 부족한 품목은 구매를 선택할 시 해당 제품을 판매하는 외부 사이트로 이동할 수 있도록 하였습니다.

<br>

### 전사게시판
<img src="https://github.com/user-attachments/assets/321f6fbb-2cfb-44b4-a44b-cbcf49773e47"> <br>
전사게시판
- 관리자 권한만 게시글을 작성할 수 있습니다.
- 게시글은 본인이 작성한 글에 대해서만 수정 및 삭제할 수 있습니다.

<br>

### 마이페이지
<img src="https://github.com/user-attachments/assets/58ca7950-1b40-447c-8753-df79ef7867f7">
<br>
<img src="https://github.com/user-attachments/assets/d74f5f95-ac96-4252-9874-133a56877be2"> 
<br>

마이페이지
- 로그인한 정보를 확인할 수 있습니다.
- 프로필 사진, 주소, 핸드폰 번호, 비밀번호를 변경할 수 있습니다.
  - 주소 변경은 좀 더 정확하게 하기 위해 다음 주소 API를 연결하였습니다.
- 본인이 작성한 댓글 및 입양, 구조동물 게시글 내역을 확인할 수 있습니다.
- 원하는 날짜에 To-Do List를 작성할 수 있으며 작성하면 라벨로 확인할 수 있습니다.

<br>

### 직원정보
<img src="https://github.com/user-attachments/assets/c8a08018-962f-4788-9945-4772694e149f"> <br>
직원정보
- 사내 직원정보 및 조직도를 확인 할 수 있습니다.
- 직원정보를 선택하여 댓글도 작성할 수 있도록 하였습니다.

<br>

### 결재요청
<img src="https://github.com/user-attachments/assets/e9d9b4db-2f64-47c1-a035-a91d9d558ddd">
<br>
<img src="https://github.com/user-attachments/assets/7c554f84-8259-4d30-b564-64b571b48d84">
<br>

결재요청
- 연차 휴가, 경조사, 연장, 퇴직원 기안을 작성할 수 있습니다.
- 연차 휴가는 본인이 갖고 있는 갯수 만큼만 기안을 올릴 수 있도록 하였습니다.
- 기안서 모두 현재 날짜보다 전 날짜로는 기안을 올릴 수 없도록 하였습니다.
- 관리자가 승인을 하기 전까진 기안을 삭제할 수 있으며 관리자가 처리한 건에 대해선 삭제할 수 없도록 하였습니다.
- 결재 승인은 관리자 -> 대표 순으로 처리가 되어야만 최종 승인 처리가 됩니다.
  
<br>

### 관리자페이지
<img src="https://github.com/user-attachments/assets/a7cb200e-8ee9-4a44-8a66-ea90ffd8987f"> <br>
관리자페이지
- 오늘의 할 일을 통해 본인 사번 앞으로 결재 기안이 올라온 건에 대해서 한 번에 확인할 수 있습니다.
- 오늘의 근태 현황은 현재 날짜 기준으로 전 직원의 출 퇴근 기록을 확인할 수 있습니다.
- 입양 진행 현황 및 품종 수를 확인할 수 있습니다.
- To-Do List를 작성할 수 있습니다.

<br>

<img src="https://github.com/user-attachments/assets/76f1d30d-6aef-4c75-be67-45c0594fcb98"> <br>
사원등록
- 관리자는 신입사원을 등록할 수 있습니다.
- 프로필 이미지를 등록하면 현재 DB에 없는 사번으로 자동 부여가 됩니다.
- 다음 주소 API 연동하여 정확한 주소지로 입력할 수 있도록 하였습니다.

<br>

<img src="https://github.com/user-attachments/assets/0697e41d-faed-427a-b025-34ec369cb4f3"> <br>
직원관리(근태수정 요청관리)
- 근태수정 건에 대해서 확인 후 승인 및 반려를 할 수 있습니다

<br>

<img src="https://github.com/user-attachments/assets/0e65b5d6-c899-43b9-b98d-5b371e36beb6"> <br>
직원관리(연차 & 휴가 관리)
- 근태율에 따라 연차 및 휴가를 부여할 수 있습니다.
- 혹여나 잘못 부여했다면 차감도 가능하도록 하였습니다

<br>

<img src="https://github.com/user-attachments/assets/35b1708f-7ef2-45f9-8a9d-0cead6bb2196">
<br>
<img src="https://github.com/user-attachments/assets/7ae420c1-59a2-4d72-b03d-64d6e699f56f">
<br>

결재함
- 관리자 사번으로 넘어온 결재 건들을 한 번에 확인할 수 있습니다.
- 관리자가 먼저 승인해야만 대표가 승인 및 반려 처리할 수 있습니다.
- 관리자가 반려를 했다면 대표는 굳이 확인을 하지 않아도 되므로 관리자 측에서 바로 최종 반려처리가 됩니다.

<br>

## ✏️ERD 및 피그마 설계
- [피그마 링크](https://www.figma.com/design/sq0XE3RSMjfI374AXYnDf9/%EC%84%B8%EB%AF%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?t=IjbHGfA8eKdH9vHA-0)
- ERD 변경 되거나 추가된 엔터티가 있으므로 추 후 수정예정
<img src="https://github.com/user-attachments/assets/23554ab8-107c-4aec-9200-1ef412ed20fd">


