document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('attendance-form');

    form.addEventListener('submit', function(event) {
        // 입력값 확인
        const requestDate = document.getElementById('request-date').value;
        const attendanceTime = document.getElementById('attendance-time').value;
        const modifyTime = document.querySelector('input[name="modify_time"]').value;
        const division = document.querySelector('input[name="division"]:checked');
        const adminCode = document.getElementById('selected-manager').value;
        const workModifyReason = document.querySelector('textarea[name="workModifyReason"]').value;

        // 비어 있는 필드 확인
        if (!requestDate || !attendanceTime || !modifyTime || !division || !adminCode || !workModifyReason) {
            alert('모든 필드를 입력해 주세요.');
            event.preventDefault(); // 폼 제출 막기
        }
    });
});
