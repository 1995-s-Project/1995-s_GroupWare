// src/main/resources/static/javascript.js
var stompClient = null;
var currentUser = null;
var selectedUser = null;


// 로그인한 사용자 정보 가져오기
function getCurrentUser(callback) {
    fetch('/user/info')  // 서버에서 로그인한 사용자 정보를 가져오는 API 호출
        .then(response => response.json())  // JSON 응답을 파싱
        .then(userInfo => {
            currentUser = userInfo.code;  // 'userInfo.code'를 사용하여 currentUser 설정
            console.log('Logged in user:', currentUser);  // currentUser 값을 확인
            callback();  // currentUser가 설정된 후 callback 호출
        })
        .catch(error => {
            console.error('Error fetching user info:', error);
        });
}

// 연결 설정 및 구독
function connect() {
    // currentUser가 설정되었는지 확인 후, 설정되었으면 connect 호출
    getCurrentUser(function() {
        if (currentUser !== null) {
            console.log('Connecting to WebSocket with currentUser:', currentUser);
            var socket = new SockJS('/chat');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/messages', function (message) {
                    var chat = JSON.parse(message.body);
                    if ((chat.senderCode === currentUser && chat.receiverCode === selectedUser) ||
                        (chat.senderCode === selectedUser && chat.receiverCode === currentUser)) {
                        displayMessage(chat);
                    }
                });
                loadUsers();  // 사용자 목록 로드
            });
        } else {
            console.error("currentUser is not set yet!");
        }
    });
}

// 사용자 목록 로드
function loadUsers() {
    fetch('/employees') // 사용자 목록을 가져오는 API 호출
        .then(response => response.json())
        .then(users => {
            var userList = document.getElementById('userList');
            userList.innerHTML = ''; // 이전 목록 초기화
            users.forEach(function(user) {
                var li = document.createElement('li');
                li.appendChild(document.createTextNode(user.empName)); // 사용자 이름 표시
                li.onclick = function() {
                    console.log('Selected user:', user.empCode); // 선택된 사원 코드 출력
                    selectUser(user.empCode); // 사원 코드로 사용자 선택
                };
                userList.appendChild(li);
            });
        })
        .catch(error => console.error('Error fetching users:', error));
}



// 사용자 선택 시 처리
function selectUser(empCode) {
    console.log('Before selecting user, currentUser:', currentUser); // currentUser 값 확인
    selectedUser = empCode; // 선택된 사용자의 사원 코드 저장
    document.getElementById('chatWith').innerText = '채팅 상대: ' + empCode; // UI 업데이트
    console.log('Selected user:', selectedUser); // selectedUser 값 확인
    console.log('After selecting user, currentUser:', currentUser); // currentUser가 변하지 않았는지 확인
    loadChatHistory(); // 채팅 기록 로드
}


function sendMessage() {
    var messageInput = document.getElementById('messageInput');
    var message = messageInput.value.trim();
    if (message && selectedUser) {
        var chatMessage = {
            senderCode: currentUser, // currentUser가 올바른 값인지 확인
            receiverCode: selectedUser,
            message: message
        };
        stompClient.send("/app/send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    } else {
        console.error("Message or selected user is missing");
    }
}

// 메시지 표시
function displayMessage(chat) {
    var chatHistory = document.getElementById('chatHistory');
    var messageDiv = document.createElement('div');
    messageDiv.className = 'chat-message';
    messageDiv.innerHTML = '<span class="sender">' + chat.senderCode + ':</span> ' + chat.message;
    chatHistory.appendChild(messageDiv);
    chatHistory.scrollTop = chatHistory.scrollHeight;
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
