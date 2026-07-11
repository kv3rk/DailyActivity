document.addEventListener('DOMContentLoaded', function() {
    const customEditionBtn = document.getElementById('custom-edition-btn');
    const customModalOverlay = document.getElementById('custom-activity-modal-overlay');
    const customModalClose = document.getElementById('custom-modal-close-x');
    const customActivitySubmit = document.getElementById('custom-activity-submit-btn');

    const customActivityType = document.getElementById('custom-activity-type');
    const customActivityTime = document.getElementById('custom-activity-time');
    const customActivityComment = document.getElementById('custom-activity-comment');

    if (!customEditionBtn) return;

    // Open modal
    customEditionBtn.addEventListener('click', function() {
        customModalOverlay.style.display = 'flex';
        customActivityTime.value = '';
    });

    // Close modal
    function closeCustomModal() {
        customModalOverlay.style.display = 'none';
    }

    customModalClose?.addEventListener('click', closeCustomModal);

    // Close on overlay click
    customModalOverlay.addEventListener('click', function(e) {
        if (e.target === customModalOverlay) {
            closeCustomModal();
        }
    });

    // Submit custom activity
    customActivitySubmit.addEventListener('click', function() {
        const activityType = customActivityType.value;
        const comment = customActivityComment.value;
        const timerValue = Number(customActivityTime.value);

        if (!timerValue || timerValue <= 0 || !Number.isInteger(timerValue)) {
            const errorModalOverlay = document.getElementById('error-modal-overlay');
            const errorModalMessage = document.getElementById('error-modal-message');
            if (errorModalOverlay && errorModalMessage) {
                errorModalMessage.innerText = 'Please enter a valid time in minutes';
                errorModalOverlay.style.display = 'flex';
            } else {
                alert('Please enter a valid time in minutes');
            }
            return;
        }

        fetch('/daily/save/timer/activity', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                activityType: activityType,
                comment: comment,
                timer: timerValue
            })
        })
            .then(response => {
                if (!response.ok) throw new Error('Network response was not ok');
                closeCustomModal();
                customActivityComment.value = '';
                customActivityTime.value = '';
            })
            .catch(error => {
                console.error('Error saving custom activity:', error);
                const errorModalOverlay = document.getElementById('error-modal-overlay');
                const errorModalMessage = document.getElementById('error-modal-message');
                if (errorModalOverlay && errorModalMessage) {
                    errorModalMessage.innerText = 'Failed to save custom activity. Please try again.';
                    errorModalOverlay.style.display = 'flex';
                }
            });
    });
});