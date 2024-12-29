function loadContent(page) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', page, true);
    xhr.onload = function() {
        const contentElement = document.getElementById('change-screen-content');
        if (xhr.status === 200) {
            contentElement.innerHTML = xhr.responseText;
            // URL을 변경
            history.pushState({ page: page }, '', page);
            // 현재 URL을 표시할 요소 업데이트
            document.getElementById('current-url').innerText = `현재 URL: ${page}`;
            // 이벤트 리스너 재설정
            setupEventListeners(); // 이 함수는 필요한 이벤트 리스너를 설정합니다.
        } else {
            contentElement.innerHTML = '페이지를 찾을 수 없습니다';
            console.error(`Error: ${xhr.status} - ${xhr.statusText}`); // 에러 로그 추가
        }
    };
    xhr.onerror = function() {
        const contentElement = document.getElementById('change-screen-content');
        contentElement.innerHTML = '서버에 연결할 수 없습니다';
        console.error('Network error occurred');
    };
    xhr.send();
}

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
            // 이미지 경로가 /img/profile/로 시작하지 않으면 경로를 붙여줌
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
            if (data.userRole === 'USER') {
                adminMenu.style.display = 'none'; // USER일 경우 관리자 메뉴 숨기기
            } else if (data.userRole === 'ADMIN') {
                adminMenu.style.display = 'block'; // ADMIN일 경우 관리자 메뉴 보이기
            }
        })
        .catch(error => {
            console.error('Error loading user info:', error);
            // 사용자 정보 로드 실패 시 기본 메시지 표시
            document.getElementById('unique-user-name-display').innerText = '사용자 정보를 불러올 수 없습니다';
        });
}

function confirmLogoutUnique() {
    const confirmation = confirm("로그아웃 하시겠습니까?");
    if (confirmation) {
        document.getElementById("logout-form-unique").submit(); // 확인 시 폼 제출
    }
    return false; // 기본 폼 제출 방지
}


function setupEventListeners() {
    // 예를 들어, 로드된 콘텐츠에 버튼이 있다고 가정합니다.
    const button = document.querySelector('#someButton'); // 로드된 콘텐츠 내의 버튼 선택
    if (button) {
        button.addEventListener('click', function() {
            alert('버튼이 클릭되었습니다!');
            // 추가적인 동작을 여기에 작성할 수 있습니다.
        });
    }

    // 다른 이벤트 리스너를 추가할 수 있습니다.
    const links = document.querySelectorAll('.dynamic-link'); // 로드된 콘텐츠 내의 링크 선택
    links.forEach(link => {
        link.addEventListener('click', function(event) {
            event.preventDefault(); // 기본 링크 동작 방지
            const newPage = this.getAttribute('href'); // 링크의 href 속성 가져오기
            loadContent(newPage); // 새로운 페이지 로드
        });
    });
}

document.addEventListener("DOMContentLoaded", function() {
    // 사용자 정보 로드
    loadUserInfo();

    const counter1 = document.querySelector('.counter1');
    const counter2 = document.querySelectorAll('.counter2');

    const updateCount = (counter, target, speed) => {
        let count = 0; // 현재 숫자
        const increment = Math.ceil(target / (speed / 100)); // 증가량

        const interval = setInterval(() => {
            count += increment;
            if (count >= target) {
                count = target; // 목표 숫자에 도달하면 멈춤
                clearInterval(interval);
            }
            counter.innerText = count; // 현재 숫자 업데이트
        }, 100); // 100ms마다 업데이트
    };

    // 카운터1: 빠르게 증가
    updateCount(counter1, 452, 4000);

    // 카운터2: 느리게 증가
    counter2.forEach(counter => {
        updateCount(counter, parseInt(counter.innerText), 8000);
    });
});










