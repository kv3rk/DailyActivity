function showErrorModal(message) {
    const overlay = document.getElementById("error-modal-overlay");
    const text = document.getElementById("error-modal-message");

    text.innerText = message;
    overlay.style.display = "flex";
}

function hideErrorModal() {
    const overlay = document.getElementById("error-modal-overlay");
    overlay.style.display = "none";
}

document.addEventListener("DOMContentLoaded", () => {
    const closeBtn = document.getElementById("error-modal-close");

    closeBtn.addEventListener("click", hideErrorModal);
});

async function handleResponse(response) {

    if (response.ok) {
        return await response.json();
    }

    let errorText = "Unknown error";

    try {
        const errorData = await response.json();

        if (errorData.errors && Array.isArray(errorData.errors)) {
            errorText = errorData.errors.join("\n");
        } else if (errorData.message) {
            errorText = errorData.message;
        }

    } catch (e) {
        errorText = "Server returned invalid error response";
    }

    showErrorModal(errorText);

    throw new Error(errorText);
}