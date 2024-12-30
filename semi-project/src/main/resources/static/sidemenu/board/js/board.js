function loadUserInfo() {
    fetch('/user/info')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json(); // JSON 형식으로 응답을 파싱
        })
        .then(data => {
            console.log('현재 권한', data.userRole);

            // 프로필 사진 URL 처리
            const profilePictureUrl = data.profilePictureUrl || '';
            document.getElementById('unique-user-profile-picture').src =
                profilePictureUrl.startsWith('/img/profile/') ? profilePictureUrl : `/img/profile/${profilePictureUrl}`;
            console.log('이미지 정보', profilePictureUrl);

            // 사용자 정보를 HTML 요소에 업데이트
            document.getElementById('unique-user-name-display').innerText = data.name || '이름 없음';

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
            document.getElementById('unique-user-department').innerText = departmentName; // 부서명 설정

            // 직급 코드와 직급명을 매핑하는 객체
            const positionMap = {
                'J1': '대표',
                'J2': '부사장',
                'J3': '부장',
                'J4': '과장',
                'J5': '팀장',
                'J6': '대리',
                'J7': '주임',
                'J8': '사원'
                // 필요한 만큼 추가
            };

            // 직급 코드에 따라 직급명을 설정
            const positionName = positionMap[data.position] || '알 수 없는 직급'; // 기본값 설정
            document.getElementById('unique-user-position').innerText = positionName; // 직급명 설정

            // 사용자 역할에 따라 관리자 메뉴 표시/숨기기
            const adminMenu = document.querySelector('.admin-menu');
            const writeButton = document.getElementById('write-button'); // 버튼 요소 선택
            if (data.userRole === 'USER') {
                adminMenu.style.display = 'none'; // USER일 경우 관리자 메뉴 숨기기
                writeButton.style.display = 'none'; // USER일 경우 글쓰기 버튼 숨기기
            } else if (data.userRole === 'ADMIN') {
                adminMenu.style.display = 'block'; // ADMIN일 경우 관리자 메뉴 보이기
                writeButton.style.display = 'block'; // ADMIN일 경우 글쓰기 버튼 보이기
            }
        })
        .catch(error => {
            console.error('Error loading user info:', error);
            // 사용자 정보 로드 실패 시 기본 메시지 표시
            document.getElementById('unique-user-name-display').innerText = '사용자 정보를 불러올 수 없습니다';
        });
}

// 페이지 로드 시 사용자 정보 로드
document.addEventListener('DOMContentLoaded', loadUserInfo);
