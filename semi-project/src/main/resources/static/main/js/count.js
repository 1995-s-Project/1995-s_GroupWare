document.addEventListener("DOMContentLoaded", function() {
    // 사용자 정보 로드
    loadUserInfo();

    const counter1 = document.querySelector('.counter1');
    const counter2 = document.querySelector('.counter2');
    const counter3 = document.querySelector('.counter3');

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

    // 누적 입양수 가져오기
    fetch('/api/adoption-success')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(adoptionData => {

            updateCount(counter1, adoptionData.length, 4000);

            // 현재 입양 신청자 수 가져오기
            return fetch('/api/adoption-status');
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(statusData => {

            updateCount(counter2, statusData.length, 8000); // 현재 입양 신청자 카운터 업데이트

            // 보호중인 유기동물 수 가져오기
            return fetch('/api/breed-counts');
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(breedCountsData => {
            console.log('보호중인 유기동물 수', breedCountsData.length || 0); // API 응답에 따라 수정 필요

            // 카운터 업데이트
            updateCount(counter3, breedCountsData.length, 8000); // 보호중인 유기동물 카운터 업데이트
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
});
