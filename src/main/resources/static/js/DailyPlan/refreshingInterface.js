async function refreshingInterface() {

    await refreshActiveGoals();

    await refreshDoneGoals();
}

setInterval(refreshingInterface, 60 * 1000);

async function refreshActiveGoals() {

    const res = await fetch("/daily/active");

    const data = await res.json();

    const activeGoalsList =
        document.getElementById("list-active-goals");

    activeGoalsList.innerHTML = "";

    data.forEach(goal => {

        createActiveListElement(goal);
    });
}

async function refreshDoneGoals() {

    const res = await fetch("/daily/done");

    const data = await res.json();

    const doneGoalsList =
        document.getElementById("list-done-goals");

    doneGoalsList.innerHTML = "";

    data.forEach(goal => {

        createDoneListElement(goal);
    });
}