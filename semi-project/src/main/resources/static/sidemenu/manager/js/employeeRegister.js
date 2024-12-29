async function fetchEmployeeInfo() {
    try {
        const response = await fetch('/api/employeeCode'); // 직원 정보 API 엔드포인트
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const data = await response.json(); // JSON 형식으로 응답 받기

        // 마지막 empCode 찾기
        const lastEmployee = data[data.length - 1];
        const lastCode = lastEmployee ? parseInt(lastEmployee.empCode) : 0; // 마지막 empCode
        const newCode = lastCode + 1; // 새로운 empCode

        // 사원번호를 3자리 문자열로 포맷팅
        const formattedCode = newCode.toString().padStart(3, '0');

        // 사원번호 입력창에 새로운 empCode 설정
        document.getElementById('code').value = formattedCode;

    } catch (error) {
        console.error('Fetch error:', error);
    }
}


function handleImageUpload(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function(e) {
        const img = document.getElementById('profilePreview');
        img.src = e.target.result;
        img.style.display = 'block'; // 이미지가 선택되면 보이도록 설정
    }

    if (file) {
        reader.readAsDataURL(file);
        // 직원 정보 가져오기
        fetchEmployeeInfo();
    }
}

function openAddressSearch() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 선택된 주소 결과 처리
            const roadAddress = data.roadAddress; // 도로명 주소
            const jibunAddress = data.jibunAddress; // 지번 주소

            // 화면에 표시 (도로명 주소 우선)
            const selectedAddress = roadAddress || jibunAddress || "주소 정보 없음";
            document.getElementById("address").value = selectedAddress; // 주소 입력 필드에 값 설정
        }
    }).open();
}

function validateForm() {
    const requiredFields = [
        document.getElementById('hireDate'),
        document.getElementById('gender'),
        document.getElementById('authority'),
        document.getElementById('code'), // 사원번호 추가
        document.getElementById('name'),
        document.getElementById('jobCode'),
        document.getElementById('phone'),
        document.getElementById('deptCode'),
        document.getElementById('address'),
        document.getElementById('email')
    ];

    // 프로필 사진 확인
    const profilePic = document.getElementById('profilePreview');
    if (!profilePic.src) {
        alert('프로필 사진을 업로드해주세요.');
        return false; // 폼 제출을 막음
    }

    for (let field of requiredFields) {
        if (!field.value) {
            alert('모든 필드를 입력해주세요.');
            return false; // 폼 제출을 막음
        }
    }

    // 신규 사원 등록 확인
    const confirmRegistration = confirm('신규 사원 등록을 하시겠습니까?');
    if (confirmRegistration) {
        alert('정상적으로 등록 처리 되었습니다.');
        return true; // 폼 제출 허용
    } else {
        return false; // 폼 제출을 막음
    }
}