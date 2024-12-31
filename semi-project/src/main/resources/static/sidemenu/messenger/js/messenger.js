var stompClient = null;
var currentUser = null;
var selectedUser = null;
var employees = []; // 직원 목록을 저장할 배열
let unreadMessagesCount = {}; // 읽지 않은 메시지 수를 저장할 객체
let processedMessages = new Set(); // 이미 처리된 메시지 ID를 저장할 Set


// 로그인한 사용자 정보 가져오기
function getCurrentUser(callback) {
    fetch('/user/info')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(userInfo => {
            currentUser = userInfo.code;
            console.log('Logged in user:', currentUser);
            loadInitialMessages(currentUser);
            callback();
        })
        .catch(error => {
            console.error('Error fetching user info:', error);
        });
}

// 연결 설정 및 구독
function connect() {
    getCurrentUser(function() {
        if (currentUser) {
            console.log('Connecting to WebSocket with currentUser:', currentUser);
            var socket = new SockJS('/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function (message) {
                    var chat = JSON.parse(message.body);
                    handleIncomingMessage(chat);
                });
                loadUsers();
            });
        } else {
            console.error("currentUser is not set yet!");
        }
    });
}

// 수신된 메시지 처리
function handleIncomingMessage(chats) {

    // 각 메시지 처리
    chats.forEach(chat => {
        // 읽지 않은 메시지인지 확인하고, 이미 처리된 메시지가 아닌지 확인
        if (!chat.isRead && !processedMessages.has(chat.id)) {
            // 읽지 않은 메시지 수 증가 (발신자에 대해서만)
            unreadMessagesCount[chat.senderCode] = (unreadMessagesCount[chat.senderCode] || 0) + 1;

            // 처리된 메시지 ID를 Set에 추가
            processedMessages.add(chat.id);

            console.log('읽지 않은 메시지 수 증가 (발신자에 대해서만)', unreadMessagesCount);
        }
    });

    // 업데이트된 카운트를 HTML에 표시
    updateUnreadMessageCount();
}

// 읽지 않은 메시지 수를 HTML에 업데이트하는 함수
function updateUnreadMessageCount() {
    const userList = document.getElementById('userList'); // 사용자 목록을 표시할 요소
    userList.innerHTML = ''; // 기존 내용 초기화

    for (const [senderCode, count] of Object.entries(unreadMessagesCount)) {
        const li = document.createElement('li');
        li.textContent = `User: ${senderCode}`;

        // 읽지 않은 메시지 수가 0보다 클 경우 빨간색 동그라미 표시
        if (count > 0) {
            const span = document.createElement('span');
            span.textContent = count;
            span.style.color = 'white';
            span.style.backgroundColor = 'red';
            span.style.borderRadius = '50%';
            span.style.padding = '5px';
            span.style.marginLeft = '10px';
            span.style.display = 'inline-block';
            span.style.width = '20px';
            span.style.height = '20px';
            span.style.textAlign = 'center';
            span.style.lineHeight = '20px'; // 수직 중앙 정렬

            li.appendChild(span);
        }

        userList.appendChild(li);
    }
}

// 사용자 목록을 표시하는 함수
function displayUsers(users) {
    var userList = document.getElementById('userList');
    userList.innerHTML = ''; // 이전 목록 초기화
    var today = new Date().toISOString().split('T')[0];

    users.forEach(function(user) {
        var li = document.createElement('li');
        li.className = 'user-item';

        // 프로필 이미지 추가
        var img = document.createElement('img');
        img.src = user.profile_image && user.profile_image.startsWith('/img/profile/')
            ? user.profile_image
            : '/img/profile/' + (user.profile_image || 'default.png');
        img.alt = user.empName + '의 프로필 이미지';
        img.className = 'profile-image';

        // 사용자 정보 추가
        var userInfo = document.createElement('div');
        userInfo.className = 'user-info';
        userInfo.innerHTML = `
            <div class="department">${user.deptDTO.deptName}</div>
            <div class="name-job">
                <strong>${user.empName}</strong> - ${user.jobDTO.jobName}
            </div>
        `;

        // 읽지 않은 메시지 수 표시
        var unreadCount = unreadMessagesCount[user.empCode] || 0;
        var statusIndicator = document.createElement('div');
        statusIndicator.className = 'status-indicator';

        // 읽지 않은 메시지 수가 0보다 클 경우 빨간색 동그라미 표시
        if (unreadCount > 0) {
            var unreadBadge = document.createElement('span');
            unreadBadge.className = 'unread-badge'; // CSS 클래스 추가
            unreadBadge.innerText = unreadCount; // 읽지 않은 메시지 수
            unreadBadge.style.color = 'white';
            unreadBadge.style.backgroundColor = 'red';
            unreadBadge.style.borderRadius = '50%';
            unreadBadge.style.padding = '5px';
            unreadBadge.style.marginRight = '170px'; // 오른쪽 여백 추가
            unreadBadge.style.display = 'inline-block';
            unreadBadge.style.width = '20px';
            unreadBadge.style.height = '20px';
            unreadBadge.style.textAlign = 'center';
            unreadBadge.style.lineHeight = '20px'; // 수직 중앙 정렬

            // 클릭 이벤트 추가
            unreadBadge.onclick = function(event) {
                event.stopPropagation(); // 부모 요소의 클릭 이벤트가 발생하지 않도록 방지
            };


            // 상태 표시기 앞에 배지 추가
            statusIndicator.appendChild(unreadBadge);
        }

        // 출근 및 퇴근 상태 표시
        statusIndicator.innerHTML += getStatusIndicator(user.scheduleDTO, today);

        // 클릭 이벤트 추가
        li.onclick = function() {
            console.log('Selected user:', user.empCode);
            selectUser(user.empCode);
            console.log('읽지 않은 메시지 보낸 사번 코드:', user.empCode); // 추가된 로그
            markMessagesAsRead(user.empCode); // 메시지를 읽음 처리하는 함수 호출
            unreadMessagesCount[user.empCode] = 0; // 읽지 않은 메시지 수 초기화
            updateUserList();
        };

        li.appendChild(img);
        li.appendChild(userInfo);
        li.appendChild(statusIndicator);
        userList.appendChild(li);
    });
}

// 메시지 읽음 처리 함수 구현
function markMessagesAsRead(senderCode) {

    console.log('보낸 사번 코드', senderCode)
    // API 요청을 통해 메시지를 읽음 처리
    fetch(`/chat/${senderCode}/markAsRead`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ senderCode: senderCode })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 좋지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            // 성공적으로 업데이트된 경우
            console.log(`User ${senderCode}의 모든 메시지를 읽음 처리했습니다.`, data);
            // 읽지 않은 메시지 수를 0으로 초기화
            unreadMessagesCount[senderCode] = 0;
            // 사용자 목록 업데이트
            updateUserList();
        })
        .catch(error => {
            console.error('메시지 읽음 처리 중 오류 발생:', error);
        });
}


// 초기화 함수 호출
getCurrentUser(); // 사용자 정보를 가져오는 함수 호출

// 출근 및 퇴근 상태 표시 함수
function getStatusIndicator(scheduleDTO, today) {
    if (scheduleDTO.workEndTime) {
        return `<span class="red-circle"></span> 퇴근`;
    } else if (scheduleDTO.workStartTime && scheduleDTO.workStartTime.startsWith(today)) {
        return `<span class="green-circle"></span> 업무중`;
    } else {
        return `<span class="gray-circle"></span> 출근 전`;
    }
}

// 사용자 목록 업데이트 함수
function updateUserList() {
    loadUsers(); // 사용자 목록을 다시 로드
    displayUsers();
}

// 페이지 로드 시 사용자 목록 및 초기 메시지 로드
window.onload = function() {
    connect(); // WebSocket 연결 설정
    loadUsers(); // 사용자 목록 로드
    getCurrentUser(function() {
        loadInitialMessages(); // 초기 메시지 로드
    });
    document.getElementById('messageInput').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            sendMessage();
        }
    });
}


function loadInitialMessages(currentUser) {

    fetch(`/chat/${currentUser}/unread`) // 읽지 않은 메시지를 가져오는 API 호출
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(messages => {
                handleIncomingMessage(messages); // 수신된 메시지 처리
            })
        .catch(error => {
            console.error('Error fetching initial messages:', error);
            alert('초기 메시지를 가져오는 데 실패했습니다.');
        });
}

// 사용자 목록 로드
function loadUsers() {
    fetch('/employees')
        .then(response => {
            if (!response.ok) throw new Error('Network response was not ok');
            return response.json();
        })
        .then(users => {
            employees = users;
            displayUsers(users);
            addFilterListeners();
        })
        .catch(error => {
            console.error('Error fetching users:', error);
        });
}

// 필터링 이벤트 리스너 추가
function addFilterListeners() {
    document.getElementById('nameSearch').addEventListener('input', filterUsers);
    document.getElementById('departmentFilter').addEventListener('change', filterUsers);
    document.getElementById('positionFilter').addEventListener('change', filterUsers);
}


// 필터링 및 검색 기능
function filterUsers() {
    var department = document.getElementById('departmentFilter').value;
    var position = document.getElementById('positionFilter').value;
    var name = document.getElementById('nameSearch').value.toLowerCase();

    var filteredUsers = employees.filter(function(user) {
        var matchesDepartment = department ? user.deptDTO.deptName.toLowerCase() === department.toLowerCase() : true;
        var matchesPosition = position ? user.jobDTO.jobName.toLowerCase() === position.toLowerCase() : true;
        var matchesName = user.empName.toLowerCase().includes(name);
        return matchesDepartment && matchesPosition && matchesName;
    });

    displayUsers(filteredUsers);
}

// 사용자 선택 시 처리 여기 수정좀 더해야됨
function selectUser(empCode) {
    console.log('Before selecting user, currentUser:', currentUser); // currentUser 값 확인
    selectedUser = empCode; // 선택된 사용자의 사원 코드 저장
    document.getElementById('chatWith').innerText = '채팅 상대: ' + empCode; // UI 업데이트
    console.log('Selected user:', selectedUser); // selectedUser 값 확인
    console.log('After selecting user, currentUser:', currentUser); // currentUser가 변하지 않았는지 확인
    loadChatHistory(); // 채팅 기록 로드
}

// 메시지 전송 함수
function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value.trim();
    if (message && selectedUser) {
        // 현재 사용자 정보 찾기
        var currentUserInfo = employees.find(user => user.empCode === currentUser);
        var selectedUserInfo = employees.find(user => user.empCode === selectedUser);

        if (currentUserInfo && selectedUserInfo) {
            var chatMessage = {
                senderCode: currentUser, // currentUser가 올바른 값인지 확인
                receiverCode: selectedUser,
                message: message
            };

            // 서버에 메시지 전송
            stompClient.send("/app/send", {}, JSON.stringify(chatMessage));

            // 프로필 이미지 경로와 직급 정보를 설정
            const profileImagePath = currentUserInfo.profile_image || '/img/profile/default.png'; // 현재 사용자의 프로필 이미지 경로
            const jobName = currentUserInfo.jobDTO.jobName; // 현재 사용자의 직급

            // 화면에 메시지 추가
            displayMessage({
                employeeJoinListDTO: {
                    profile_image: profileImagePath, // 프로필 이미지 경로
                    empName: currentUserInfo.empName, // 현재 사용자 이름
                    jobDTO: { jobName: jobName } // 현재 사용자 직급
                },
                message: message,
                timestamp: Date.now() // 현재 시간
            });

            // 입력 필드 초기화
            messageInput.value = '';
        } else {
            console.error("Current user or selected user information is missing");
        }
    } else {
        console.error("Message or selected user is missing");
    }
}
// 채팅 히스토리 로드
function loadChatHistory() {
    var chatHistory = document.getElementById('chatHistory');
    chatHistory.innerHTML = '';
    if (selectedUser) {
        fetch('/chat/history/' + currentUser + '/' + selectedUser)
            .then(response => response.json())
            .then(data => {
                data.forEach(function(chat) {
                    displayMessage(chat);
                });
            });
    }
}


// 페이지 로드 시 실행되는 기존 코드에 추가
window.onload = function () {
    loadUsers();

    const messageInput = document.getElementById('messageInput');
    messageInput.addEventListener('keydown', function (event) {
        // Enter 키를 누를 경우 sendMessage 함수 호출
        if (event.key === 'Enter') {
            event.preventDefault(); // 기본 동작 방지 (폼 제출 방지)
            sendMessage();
        }
    });
};
//엔터키
function checkEnter(event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
}


// 메시지 표시
function displayMessage(chat) {
    var chatHistory = document.getElementById('chatHistory');
    var messageDiv = document.createElement('div');
    messageDiv.className = 'chat-message';

    // 프로필 이미지와 메시지 정보를 포함한 HTML 구조
    const profileImagePath = chat.employeeJoinListDTO.profile_image
        ? (chat.employeeJoinListDTO.profile_image.startsWith('/img/profile/')
            ? chat.employeeJoinListDTO.profile_image
            : '/img/profile/' + chat.employeeJoinListDTO.profile_image)
        : '/img/profile/default-profile.png';

    messageDiv.innerHTML = `
        <div class="message-container">
            <div class="message-details">
                <div class="name-job">
                    <div class="profile-image">
                        <img src="${profileImagePath}" alt="${chat.employeeJoinListDTO.empName}" />
                    </div>
                    <div class="message-container">
                        <div class="message-box">
                            <span class="name">${chat.employeeJoinListDTO.empName}</span>
                            <span class="job">${chat.employeeJoinListDTO.jobDTO.jobName}</span>
                        </div>
                        <div class="message-text">
                            <span class="sender"></span> ${chat.message}
                            <div class="timestamp">${new Date(chat.timestamp).toLocaleString()}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    `;

    chatHistory.appendChild(messageDiv);
    chatHistory.scrollTop = chatHistory.scrollHeight;
}





