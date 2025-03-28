// 로그인한 사용자 정보를 가져오는 함수
async function fetchUserInfo() {
    try {
        const response = await fetch('/api/approvalUser'); // 사용자 정보를 가져오는 API 엔드포인트
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const userData = await response.json();
        return userData;
    } catch (error) {
        console.error('Error fetching user info:', error);
    }
}

// 부서 코드를 부서명으로 변환하는 함수
function getDepartmentName(deptCode) {
    const departmentMap = {
        'B1': '경영부',
        'B2': '애견담당부',
        'B3': '마케팅부',
        'B4': '영업부',
        'B5': '물류부',
        'B6': '고객CS업무부'
    };
    return departmentMap[deptCode] || '부서 정보 없음';
}

async function populateUserInfo() {
    const userInfo = await fetchUserInfo();
    if (userInfo) {
        document.getElementById('department').textContent = getDepartmentName(userInfo.deptCode);
        const authorElement = document.getElementById('author');
        authorElement.textContent = userInfo.name || '이름 정보 없음';
        authorElement.setAttribute('empCode', userInfo.empCode || '사번 정보 없음');
    }

    const today = new Date();
    const formattedDate = today.getFullYear() + '-' +
        String(today.getMonth() + 1).padStart(2, '0') + '-' +
        String(today.getDate()).padStart(2, '0');

    // 날짜를 표시하는 td 요소에 설정
    document.getElementById('date').textContent = formattedDate;

    // 숨겨진 입력 필드에 날짜 값을 설정
    document.getElementById('deadline_date').value = formattedDate;
}


// 직원 목록을 가져오는 함수
async function fetchEmployeeList() {
    try {
        const response = await fetch('/api/employees'); // 직원 정보를 가져오는 API 엔드포인트
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const employees = await response.json();
        return employees;
    } catch (error) {
        console.error('Error fetching employee list:', error);
    }
}

// 필터링 함수
function filterEmployees() {
    const departmentFilter = document.getElementById('departmentFilter').value;
    const positionFilter = document.getElementById('positionFilter').value;

    const employeeListModal = document.getElementById('employeeListModal');
    const employees = Array.from(employeeListModal.children); // 현재 직원 목록을 배열로 변환

    employees.forEach(employee => {
        const empDept = employee.children[3].textContent; // 부서 이름
        const empJob = employee.children[2].textContent; // 직급 이름

        // 필터 조건에 맞지 않으면 숨김
        const matchesDepartment = !departmentFilter || empDept === departmentFilter;
        const matchesPosition = !positionFilter || empJob === positionFilter;

        if (matchesDepartment && matchesPosition) {
            employee.style.display = ''; // 조건에 맞으면 보이기
        } else {
            employee.style.display = 'none'; // 조건에 맞지 않으면 숨기기
        }
    });
}

// 모달 창 열기
async function openEmployeeModal() {
    console.log('모달 열기 함수 호출됨'); // 디버깅 로그
    const employeeListModal = document.getElementById('employeeListModal');
    employeeListModal.innerHTML = '<li>로딩 중...</li>'; // 로딩 상태 표시

    try {
        const response = await fetch('/api/employees'); // 직원 정보를 가져오는 API 엔드포인트
        if (!response.ok) {
            throw new Error('네트워크 응답이 좋지 않습니다.');
        }
        const employeeList = await response.json(); // JSON 데이터 파싱
        employeeListModal.innerHTML = ''; // 기존 목록 초기화

        if (employeeList && employeeList.length > 0) {
            employeeList.forEach(employee => {
                const empCode = employee.empCode || 'N/A'; // 사번
                const empName = employee.empName || 'N/A'; // 이름
                const jobName = employee.jobDTO ? employee.jobDTO.jobName : 'N/A'; // 직급 이름
                const deptName = employee.deptDTO ? employee.deptDTO.deptName : 'N/A'; // 부서 이름

                const li = document.createElement('li');
                li.innerHTML = `
                    <span>${empCode}</span>
                    <span>${empName}</span>
                    <span>${jobName}</span>
                    <span>${deptName}</span>
                `; // 직원 정보
                li.setAttribute('data-name', empName); // 데이터 속성 추가
                li.onclick = () => selectEmployee(employee); // 직원 객체를 전달
                employeeListModal.appendChild(li);
            });
        } else {
            employeeListModal.innerHTML = '<li>직원 정보가 없습니다.</li>';
        }

        document.getElementById('employeeModal').style.display = 'block'; // 모달 열기
        console.log('모달이 열렸습니다.'); // 디버깅 로그

        // 필터링 이벤트 리스너 추가
        document.getElementById('departmentFilter').addEventListener('change', filterEmployees);
        document.getElementById('positionFilter').addEventListener('change', filterEmployees);

    } catch (error) {
        console.error('직원 목록을 가져오는 중 오류 발생:', error);
        employeeListModal.innerHTML = '<li>직원 목록을 가져오는 중 오류가 발생했습니다.</li>'; // 오류 메시지 표시
    }
}

// 직원 선택 시 호출되는 함수
function selectEmployee(employee) {
    const managerCell = document.getElementById('managerCell'); // id로 매니저 셀 선택
    managerCell.textContent = employee.empName; // 선택한 직원의 이름 추가
    managerCell.setAttribute('adminCode', employee.empCode); // 사번 코드 추가
    // managerCell.setAttribute('adminName', employee.empName); // 이 줄은 필요 없음

    // 숨겨진 필드에 값 설정
    document.getElementById('adminCode').value = employee.empCode || 'N/A'; // adminCode 설정
    document.getElementById('adminName').value = employee.empName || 'N/A'; // adminName 설정

    closeEmployeeModal(); // 모달 닫기
}


// 모달 닫기
function closeEmployeeModal() {
    document.getElementById('employeeModal').style.display = 'none';
}

// DOMContentLoaded 이벤트가 발생하면 populateUserInfo 함수 실행
document.addEventListener('DOMContentLoaded', populateUserInfo);

// 이벤트 리스너 추가
document.getElementById('showEmployeeList').addEventListener('click', openEmployeeModal);
document.getElementById('closeModal').addEventListener('click', closeEmployeeModal);

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
    const modal = document.getElementById('employeeModal');
    if (event.target === modal) {
        closeEmployeeModal();
    }
};

function checkOverTime() {
    const overTimeInput = document.getElementById('overTime');
    const overTimeValue = overTimeInput.value;

    if (overTimeValue) {
        const [hours, minutes] = overTimeValue.split(':').map(Number);
        const selectedTime = new Date();
        selectedTime.setHours(hours, minutes, 0, 0); // 선택한 시간 설정

        const currentTime = new Date(); // 현재 시간

        // 현재 시간보다 선택한 시간이 빠른 경우
        if (selectedTime < currentTime) {
            alert("현재 시간보다 빠른 시간으로 설정하실 수 없습니다.");
            overTimeInput.value = ''; // 입력 필드 비우기
        }
    }
}


document.getElementById('submitButton').addEventListener('click', function (event) {
    // 기본 동작 방지
    event.preventDefault();

    const managerCell = document.getElementById('managerCell').innerHTML.trim();
    const overTimeReason = document.querySelector('textarea[name="overTimeReason"]').value.trim();
    const overTimeValue = document.getElementById('overTime').value.trim(); // 타임값 가져오기

    // 입력 필드가 비어 있는지 확인
    if (!managerCell || !overTimeReason || !overTimeValue) {
        alert("모든 필드를 입력해 주세요.");
        return; // 입력이 부족하면 함수 종료
    }

    // 모든 필드가 채워진 경우, 폼 제출 또는 다른 작업 수행
    const confirmation = confirm("제출하시겠습니까?");
    if (confirmation) {
        // 기본 동작 방지 제거
        document.getElementById('overTimeFormRequest').submit(); // 폼 제출
    }
});







