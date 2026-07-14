// ========= TELEGRAM STATUS CHECK =========

const TELEGRAM_ENDPOINT = '/daily/settings';
const BOT_USERNAME = 'n0t1fying_bot';

async function checkTelegramStatus() {
    try {
        const response = await fetch(`${TELEGRAM_ENDPOINT}/get/telegram`);
        if (!response.ok) throw new Error("Failed to fetch Telegram status");

        const data = await response.json();
        const wrap = document.getElementById("telegram-status-wrap");
        if (!wrap) return;

        // If telegram field is present and non-empty
        if (data.telegram && data.telegram.trim() !== "") {
            wrap.innerHTML = `
                <div class="telegram-connected">
                    <span class="telegram-connected-text">Telegram already connected</span>
                    <button type="button" class="telegram-unlink-btn" id="telegram-unlink-btn">Unlink Telegram</button>
                </div>
            `;
        } else {
            wrap.innerHTML = `
                <button type="button" class="telegram-connect-btn" id="telegram-connect-btn">Connect Telegram</button>
            `;
        }
    } catch (err) {
        console.error("Check Telegram status failed:", err);
        // Fallback: show connect button
        const wrap = document.getElementById("telegram-status-wrap");
        if (wrap) {
            wrap.innerHTML = `
                <button type="button" class="telegram-connect-btn" id="telegram-connect-btn">Connect Telegram</button>
            `;
        }
    }
}

// ========= TELEGRAM CONNECT =========

async function connectTelegram() {
    try {
        const response = await fetch(`${TELEGRAM_ENDPOINT}/set/telegram`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error("Failed to generate Telegram link");
        }

        const uuid = await response.text();
        const telegramLink = `https://telegram.me/${BOT_USERNAME}?start=${uuid}`;
        window.open(telegramLink, '_blank');

    } catch (err) {
        console.error("Connect Telegram failed:", err);
    }
}

// ========= TELEGRAM UNLINK =========

async function unlinkTelegram() {
    try {
        const response = await fetch(`${TELEGRAM_ENDPOINT}/delete/telegram`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error("Failed to unlink Telegram");
        }

        // Refresh UI — show connect button again
        checkTelegramStatus();

    } catch (err) {
        console.error("Unlink Telegram failed:", err);
    }
}

// ========= EVENT DELEGATION =========

document.addEventListener("click", (e) => {
    if (e.target && e.target.id === "telegram-connect-btn") {
        connectTelegram();
    }
    if (e.target && e.target.id === "telegram-unlink-btn") {
        unlinkTelegram();
    }
});

// ========= INIT =========

document.addEventListener('DOMContentLoaded', checkTelegramStatus);