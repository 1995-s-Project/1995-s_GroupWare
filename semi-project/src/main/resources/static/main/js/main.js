document.addEventListener('DOMContentLoaded', function() {
    setupEventListeners(); // 페이지 로드 시 초기 이벤트 리스너 설정
});

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
