let timer = null;
let totalSeconds = 2700;
let endTime = null;
let isRunning = false;

const timerDisplay = document.getElementById("timer-display");
const minutesInput = document.getElementById("minutes-input");
const startButton = document.getElementById("start-timer-btn");
const pauseButton = document.getElementById("pause-timer-btn");
const stopButton = document.getElementById("stop-timer-btn");

const activityModalOverlay =
    document.getElementById("activity-modal-overlay");

const activityType =
    document.getElementById("activity-type");

const activityComment =
    document.getElementById("activity-comment");

const activitySubmitButton =
    document.getElementById("activity-submit-btn");


// ========= TIMER ===========

function openActivityModal() {
    activityModalOverlay.style.display = "flex";
}

function closeActivityModal() {
    activityModalOverlay.style.display = "none";
    activityComment.value = "";
}

function updateDisplay() {
    const minutes = Math.floor(totalSeconds / 60);
    const seconds = totalSeconds % 60;

    timerDisplay.innerText =
        `${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
}

function playSound() {
    const audio = new Audio(
        "https://actions.google.com/sounds/v1/alarms/alarm_clock.ogg"
    );

    audio.play().catch(error => {
        console.warn("Sound playback blocked:", error);
    });
}

function finishTimer() {
    clearInterval(timer);
    timer = null;
    isRunning = false;
    totalSeconds = 0;
    endTime = null;

    updateDisplay();
    openActivityModal();
    playSound();
}

function startTimer() {
    if (isRunning || totalSeconds <= 0) {
        return;
    }

    isRunning = true;

    // фиксируем точное время окончания
    endTime = Date.now() + totalSeconds * 1000;

    timer = setInterval(() => {
        const remaining = Math.ceil((endTime - Date.now()) / 1000);

        totalSeconds = Math.max(remaining, 0);

        updateDisplay();

        if (totalSeconds <= 0) {
            finishTimer();
        }

    }, 1000);
}

function pauseTimer() {
    if (!isRunning) {
        return;
    }

    clearInterval(timer);
    timer = null;
    isRunning = false;

    const remaining = Math.ceil((endTime - Date.now()) / 1000);
    totalSeconds = Math.max(remaining, 0);

    updateDisplay();
}

function stopTimer() {
    clearInterval(timer);
    timer = null;
    isRunning = false;
    endTime = null;

    openActivityModal();
}

function updateTimerFromInput() {
    if (isRunning) {
        return;
    }

    totalSeconds = Number(minutesInput.value) * 60;
    updateDisplay();
}


// Когда вкладка снова стала активной — пересчитать
document.addEventListener("visibilitychange", () => {
    if (document.visibilityState === "visible" && isRunning) {
        const remaining = Math.ceil((endTime - Date.now()) / 1000);

        totalSeconds = Math.max(remaining, 0);

        updateDisplay();

        if (totalSeconds <= 0) {
            finishTimer();
        }
    }
});


startButton.addEventListener("click", startTimer);
pauseButton.addEventListener("click", pauseTimer);
stopButton.addEventListener("click", stopTimer);
minutesInput.addEventListener("input", updateTimerFromInput);

updateDisplay();

activitySubmitButton.addEventListener("click", () => {
    fetch('/daily/save/timer/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            activityType: activityType.value,
            comment: activityComment.value,
            time: Math.ceil(
                (Number(minutesInput.value) * 60 - totalSeconds) / 60
            )
        })
    }).then(() => {
        closeActivityModal();

        clearInterval(timer);
        timer = null;
        isRunning = false;
        endTime = null;

        totalSeconds = Number(minutesInput.value) * 60;
        updateDisplay();
    });
});