async function add_goal(event) {

    event.preventDefault();

    const input_text = document.getElementById("textfield-goals");

    try {

        const res = await fetch('/daily/save', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                goalText: input_text.value
            })
        });

        const data = await handleResponse(res);

        input_text.value = "";

        await createActiveListElement(data);

    } catch (error) {
        console.error(error);
    }
}

async function createActiveListElement(data) {

    const activeGoalsList = document.getElementById("list-active-goals");

    const li = document.createElement("li");

    li.id = data.id;
    li.className = "list-group-item goal-item";

    const span = document.createElement("span");

    span.innerText = data.goalText;
    span.className = "goal-text";

    const button = document.createElement("button");

    button.type = "button";
    button.innerText = "Done";
    button.className = "btn btn-danger btn-sm done-button";
    button.onclick = toggleFlag;

    li.appendChild(span);
    li.appendChild(button);

    activeGoalsList.appendChild(li);
}

async function toggleFlag(event) {

    event.preventDefault();

    const button = event.target;
    const li = button.parentElement;

    try {

        const res = await fetch('/daily/toggle', {

            method: 'POST',

            headers: {
                'Content-Type': 'application/json',
            },

            body: JSON.stringify({
                id: li.id
            })
        });

        const data = await handleResponse(res);

        li.remove();

        await createDoneListElement(data);

    } catch (error) {
        console.error(error);
    }
}

async function createDoneListElement(data) {

    const doneGoalsList = document.getElementById("list-done-goals");

    const li = document.createElement("li");

    li.innerText = data.goalText;
    li.className = "list-group-item goal-item element-done-goal";

    doneGoalsList.appendChild(li);
}