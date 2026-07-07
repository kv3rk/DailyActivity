let stopwatchInterval = null;
let stopwatchStartTime = null;
let stopwatchElapsed = 0; // in milliseconds
let stopwatchRunning = false;

const stopwatchDisplay = document.getElementById("stopwatch-display");
const startStopwatchBtn = document.getElementById("start-stopwatch-btn");
const pauseStopwatchBtn = document.getElementById("pause-stopwatch-btn");
const stopStopwatchBtn = document.getElementById("stop-stopwatch-btn");

function formatStopwatchTime(ms) {
    const totalSeconds = Math.floor(ms / 1000);
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;

    return `${String(hours).padStart(2, "0")}:${String(minutes).padStart(2, "0")}:${String(seconds).padStart(2, "0")}`;
}

function updateStopwatchDisplay() {
    const now = Date.now();
    const elapsed = stopwatchElapsed + (stopwatchRunning ? (now - stopwatchStartTime) : 0);
    stopwatchDisplay.innerText = formatStopwatchTime(elapsed);
}

function startStopwatch() {
    if (stopwatchRunning) return;

    stopwatchRunning = true;
    stopwatchStartTime = Date.now();

    stopwatchInterval = setInterval(() => {
        updateStopwatchDisplay();
    }, 100);
}

function pauseStopwatch() {
    if (!stopwatchRunning) return;

    stopwatchRunning = false;
    stopwatchElapsed += Date.now() - stopwatchStartTime;

    clearInterval(stopwatchInterval);
    stopwatchInterval = null;

    updateStopwatchDisplay();
}

function stopStopwatch() {
    if (stopwatchRunning) {
        stopwatchElapsed += Date.now() - stopwatchStartTime;
    }

    clearInterval(stopwatchInterval);
    stopwatchInterval = null;
    stopwatchRunning = false;

    updateStopwatchDisplay();
    openActivityModal();
}

function resetStopwatch() {
    stopwatchElapsed = 0;
    stopwatchStartTime = null;
    stopwatchRunning = false;
    updateStopwatchDisplay();
}

function getStopwatchMinutes() {
    return Math.ceil(stopwatchElapsed / 60000);
}

startStopwatchBtn.addEventListener("click", startStopwatch);
pauseStopwatchBtn.addEventListener("click", pauseStopwatch);
stopStopwatchBtn.addEventListener("click", stopStopwatch);

activitySubmitButton.addEventListener("click", () => {

    const isStopwatchActive = document.getElementById("stopwatch-view").style.display !== "none";

    let timerValue;
    if (isStopwatchActive) {
        timerValue = getStopwatchMinutes();
    } else {
        timerValue = Math.ceil((Number(minutesInput.value) * 60 - totalSeconds) / 60);
    }

    fetch('/daily/save/timer/activity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            activityType: activityType.value,
            comment: activityComment.value,
            timer: timerValue
        })
    }).then(() => {
        closeActivityModal();
        if (isStopwatchActive) {
            resetStopwatch();
        } else {
            clearInterval(timer);
            timer = null;
            isRunning = false;
            endTime = null;
            totalSeconds = Number(minutesInput.value) * 60;
            updateDisplay();
            clearInterval(repeatAlarmInterval);
            repeatAlarmInterval = null;
        }
    });
});