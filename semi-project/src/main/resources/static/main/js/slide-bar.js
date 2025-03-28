document.addEventListener('DOMContentLoaded', function() {
    fetchAndDisplayData();
});

function fetchAndDisplayData() {
    fetch('/api/board')
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다: ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            let html = '<ul class="slide-list">';
            if (Array.isArray(data)) {
                data.forEach(function(item) {
                    html += '<li style="display: none;"><a href="/board/' + item.boardCode + '">' + item.boardTitle + '</a></li>';
                });
            } else {
                console.error('데이터 형식이 올바르지 않습니다:', data);
            }
            html += '</ul>';
            document.querySelector('.slide-board').innerHTML = html;

            // 슬라이드 효과 추가
            startSlideShow();
        });
}

function startSlideShow() {
    const slideList = document.querySelector('.slide-list');
    const items = slideList.children;
    let index = 0;

    function showNextItem() {
        if (items.length > 0) {
            // 현재 항목 숨기기
            items[index].style.opacity = '0'; // 투명하게 만들기
            items[index].style.display = 'none'; // 숨기기

            // 다음 항목으로 이동
            index = (index + 1) % items.length;

            // 다음 항목 보이기
            items[index].style.display = 'block'; // 보이도록 설정
            setTimeout(() => {
                items[index].style.opacity = '1'; // 보이도록 설정
            }, 50); // 약간의 지연 후 위치 변경

            // 4초 후에 다음 항목으로 이동
            setTimeout(showNextItem, 4000); // 4초 후에 다음 항목으로 이동
        }
    }

    // 첫 번째 항목을 활성화
    if (items.length > 0) {
        items[index].style.display = 'block'; // 첫 번째 항목 보이도록 설정
        items[index].style.opacity = '1'; // 첫 번째 항목 보이도록 설정
    }

    // 4초 후에 다음 항목으로 이동
    setTimeout(showNextItem, 4000); // 4초 후에 다음 항목으로 이동
}
