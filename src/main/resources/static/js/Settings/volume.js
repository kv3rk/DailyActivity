// ========= VOLUME SETTINGS =========

async function fetchVolume() {
    const response = await fetch('/daily/settings/get/volume');
    if (!response.ok) {
        throw new Error("Failed to load volume");
    }
    const data = await response.json();
    return data.volume;
}

async function updateVolumeDisplay() {
    const volume = await fetchVolume();

    const volumeInput = document.getElementById("volume-input");
    const volumeValueDisplay = document.getElementById("volume-value");

    if (volumeInput) {
        volumeInput.value = volume;
    }
    if (volumeValueDisplay) {
        volumeValueDisplay.innerText = volume + "%";
    }
}

async function applyVolume() {
    const volumeInput = document.getElementById("volume-input");
    const value = volumeInput ? parseInt(volumeInput.value, 10) : 0;

    try {
        await fetch('/daily/settings/set/volume', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ volume: value })
        });

        await updateVolumeDisplay();

    } catch (err) {
        console.error("Failed to save volume:", err);
    }
}

document.addEventListener("DOMContentLoaded", updateVolumeDisplay);