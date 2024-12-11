fetch('/user/info')
    .then(response => response.json())
    .then(data => {
        document.getElementById('profile-picture').src = data.profilePictureUrl;
        document.getElementById('name').innerText = `😊${data.name}님 오늘 하루도 힘내세요!😊`; // 이름과 환영 메시지 설정

        // userName 변수를 data.name으로 설정
        const userName = data.name;
        document.getElementById('user-name').textContent = userName; // 두 번째 이름

        // 부서 코드와 부서명을 매핑하는 객체
        const departmentMap = {
            'B1': '경영부',
            'B2': '애견담당부',
            'B3': '마케팅부',
            'B4': '영업부',
            'B5': '물류부',
            'B6': '고객CS 업무부',
        };

        // 부서 코드에 따라 부서명을 설정
        const departmentName = departmentMap[data.department] || '알 수 없는 부서'; // 기본값 설정
        document.getElementById('department').innerText = `${departmentName}`; // 부서명 설정

        // 직급 코드와 직급명을 매핑하는 객체
        const positionMap = {
            'J1': '대표',
            'J2': '부사장',
            'J3': '부장',
            'J4': '과장',
            'J5': '주임',
            'J6': '대리',
            'J7': '사원',
            // 필요한 만큼 추가
        };

        // 직급 코드에 따라 직급명을 설정
        const positionName = positionMap[data.position] || '알 수 없는 직급'; // 기본값 설정
        document.getElementById('position').innerText = positionName; // 직급 설정
    })
    .catch(error => console.error('Error fetching user info:', error));

function confirmLogout() {
    const confirmation = confirm("로그아웃 하시겠습니까?");
    if (confirmation) {
        document.getElementById("logout-form").submit(); // 확인 시 폼 제출
    }
    return false; // 기본 폼 제출 방지
}
