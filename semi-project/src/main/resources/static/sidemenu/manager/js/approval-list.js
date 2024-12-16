async function fetchApprovalData() {
    try {
        const response = await fetch('/api/approval-list');

        // 응답이 성공적이지 않은 경우 에러 처리
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        const data = await response.json(); // JSON 형식으로 응답 데이터 파싱

        // 결재 정보를 HTML에 추가
        const approvalList = document.querySelector('.approval');
        approvalList.innerHTML = ''; // 기존 내용을 지우기

        // 날짜 형식을 YYYY-MM-DD로 변환하는 함수
        const formatDate = (dateString) => {
            const date = new Date(dateString);
            const year = date.getFullYear();
            const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
            const day = String(date.getDate()).padStart(2, '0');
            return `${year}-${month}-${day}`;
        };

        // 상태 변환 함수
        const getStatus = (value) => {
            if (value === null) {
                return '확인중';
            } else if (value === 'Y') {
                return '승인';
            } else if (value === 'N') {
                return '반려';
            }
            return value; // 기본값 반환
        };

        // progressCode 변환 함수
        const getProgressStatus = (code) => {
            switch (code) {
                case 'SU1':
                    return '대기중';
                case 'SU2':
                    return '진행중';
                case 'SU3':
                    return '승인';
                case 'SU4':
                    return '반려';
                default:
                    return code; // 기본값 반환
            }
        };

        // 결재 목록 처리
        const allLists = [
            ...data.cacPaymentList,
            ...data.overTimeList,
            ...data.retirementList,
            ...data.vacPaymentList
        ];

        allLists.forEach(item => {
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <span>${item.empCode}</span>
                <span>${formatDate(item.deadline_date)}</span>
                <span>${item.type}</span>
                <span>${getStatus(item.managerAccept)}</span>
                <span>${getStatus(item.presidentAccept)}</span>
                <span>${getProgressStatus(item.progressCode)}</span>
            `;
            approvalList.appendChild(listItem);
        });

    } catch (error) {
        console.error('Fetch error:', error);
    }
}

// 페이지가 로드될 때 결재 정보 가져오기
window.onload = fetchApprovalData;
