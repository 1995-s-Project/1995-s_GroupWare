function loadContent(page) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', page, true);
    xhr.onload = function() {
        const contentElement = document.getElementById('change-screen-content');
        if (xhr.status === 200) {
            contentElement.innerHTML = xhr.responseText;
            // URL을 변경
            history.pushState(null, '', page);
            // 현재 URL을 표시할 요소 업데이트
            document.getElementById('current-url').innerText = `현재 URL: ${page}`;
        } else {
            contentElement.innerHTML = '페이지를 찾을 수 없습니다';
        }
    };
    xhr.send();
}




