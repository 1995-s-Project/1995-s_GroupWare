document.addEventListener("DOMContentLoaded", function() {
    const levels = document.querySelectorAll('.level');

    levels.forEach(level => {
        const cards = level.querySelectorAll('.employee-card');
        if (cards.length > 0) {
            // 연결선을 생성
            const connectionLine = document.createElement('div');
            connectionLine.className = 'connection-line';

            // 첫 번째 카드의 위치와 너비를 가져옴
            const firstCard = cards[0];
            const secondCard = cards.length > 1 ? cards[1] : null;

            const firstCardRect = firstCard.getBoundingClientRect();
            const secondCardRect = secondCard ? secondCard.getBoundingClientRect() : null;

            // 연결선의 위치와 길이를 설정
            const lineHeight = 2; // 선의 두께
            const lineLength = secondCardRect ? secondCardRect.left - firstCardRect.right : 0; // 두 카드 사이의 거리

            connectionLine.style.height = `${lineHeight}px`;
            connectionLine.style.left = `${firstCardRect.right}px`; // 첫 번째 카드의 오른쪽 끝
            connectionLine.style.top = `calc(${firstCardRect.top + firstCardRect.height / 2}px)`; // 카드 중앙에 위치
            connectionLine.style.width = `${lineLength}px`; // 두 카드 사이의 길이

            level.appendChild(connectionLine);
        }
    });
});
