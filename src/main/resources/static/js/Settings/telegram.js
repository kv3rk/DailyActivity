// ========= TELEGRAM SETTINGS =========

const TELEGRAM_ENDPOINT = '/daily/settings';

async function fetchTelegram() {
    const response = await fetch(`${TELEGRAM_ENDPOINT}/get/telegram`);
    if (!response.ok) {
        throw new Error("Failed to load telegram");
    }
    return await response.json();
}

async function saveTelegram() {
    const telegramInput = document.getElementById("telegram-username");
    const username = telegramInput ? telegramInput.value.trim() : "";

    if (!username) {
        return;
    }

    try {
        await fetch(`${TELEGRAM_ENDPOINT}/set/telegram`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ telegram: username })
        });

        refreshTelegramUI();

    } catch (err) {
        console.error("Failed to save telegram:", err);
    }
}

async function deleteTelegram() {
    try {
        await fetch(`${TELEGRAM_ENDPOINT}/delete/telegram`, {
            method: 'DELETE'
        });

        refreshTelegramUI();

    } catch (err) {
        console.error("Failed to delete telegram:", err);
    }
}

function refreshTelegramUI() {
    const telegramInput = document.getElementById("telegram-username");
    if (!telegramInput) {
        return;
    }

    fetchTelegram()
        .then(data => {
            const username = data && data.telegram ? data.telegram : null;

            if (username) {
                telegramInput.placeholder = "@" + username;
            } else {
                telegramInput.placeholder = "@username";
            }

            telegramInput.value = "";
        })
        .catch(() => {
            telegramInput.placeholder = "@username";
            telegramInput.value = "";
        });
}

document.addEventListener("DOMContentLoaded", refreshTelegramUI);

document.addEventListener("click", (e) => {
    if (!e.target) return;

    if (e.target.id === "telegram-save-btn") {
        saveTelegram();
    }

    if (e.target.id === "telegram-delete-btn") {
        deleteTelegram();
    }
});