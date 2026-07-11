// ========= TELEGRAM CONNECT =========

const TELEGRAM_ENDPOINT = '/daily/settings';
const BOT_USERNAME = 'n0t1fying_bot';

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

        const telegramLink = `https://t.me/${BOT_USERNAME}?start=${uuid}`;

        window.open(telegramLink, '_blank');

    } catch (err) {
        console.error("Connect Telegram failed:", err);
    }
}

document.addEventListener("click", (e) => {
    if (e.target && e.target.id === "telegram-connect-btn") {
        connectTelegram();
    }
});