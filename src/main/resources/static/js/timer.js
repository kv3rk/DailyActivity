let timer = null;

let totalSeconds = 2700;

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

    audio.play();
}


function startTimer() {

    if (isRunning) {
        return;
    }

    isRunning = true;

    timer = setInterval(() => {

        totalSeconds--;

        updateDisplay();

        if (totalSeconds <= 0) {

            clearInterval(timer);

            isRunning = false;

            openActivityModal();

            totalSeconds = 0;

            updateDisplay();

            playSound();

        }

    }, 1000);
}


function pauseTimer() {

    clearInterval(timer);

    isRunning = false;
}


function stopTimer() {

    clearInterval(timer);

    isRunning = false;

    openActivityModal();

}


function updateTimerFromInput() {

    if (isRunning) {
        return;
    }

    totalSeconds = Number(minutesInput.value) * 60;

    updateDisplay();
}


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
            time: Math.ceil((minutesInput.value * 60 - totalSeconds) / 60)

        })
    }).then(()=>{
        closeActivityModal();
        totalSeconds = Number(minutesInput.value) * 60;
        updateDisplay();
    });
});