// ============================================================
// ACTIVITY TYPES
// ============================================================

const ACTIVITIES_ENDPOINT = '/daily/settings';

/**
 * Shows the edit form below the activity list, pre-filled with the current name.
 * Only one edit form can be open at a time.
 * @param {number} slot - The activity slot number (1, 2, or 3)
 */
function editActivity(slot) {
    const item = document.querySelector(`.activity-type-item[data-activity-slot="${slot}"]`);
    if (!item) return;

    // Get current activity name from data attribute
    const currentName = item.getAttribute('data-activity-name') || '';

    // Hide any previously open edit form
    hideEditForm();

    // Show the edit form
    const editWrap = document.getElementById('activity-edit-wrap');
    const editInput = document.getElementById('activity-edit-input');
    const editSlotInput = document.getElementById('activity-edit-slot');

    if (editWrap && editInput && editSlotInput) {
        editWrap.style.display = 'block';
        editInput.value = currentName;
        editInput.focus();
        editSlotInput.value = slot;

        // Highlight the item being edited
        item.classList.add('editing');
    }
}

/**
 * Hides the edit form and clears any editing highlights.
 */
function hideEditForm() {
    const editWrap = document.getElementById('activity-edit-wrap');
    const editInput = document.getElementById('activity-edit-input');
    const editSlotInput = document.getElementById('activity-edit-slot');

    if (editWrap) editWrap.style.display = 'none';
    if (editInput) editInput.value = '';
    if (editSlotInput) editSlotInput.value = '';

    // Remove editing highlight from all items
    document.querySelectorAll('.activity-type-item.editing').forEach(el => {
        el.classList.remove('editing');
    });
}

/**
 * Collects current activity values from the DOM and builds the full DTO
 * with activity1, activity2, activity3 fields.
 * @returns {Object} Object with activity1, activity2, activity3
 */
function buildActivitiesDTO() {
    const dto = {
        activity1: null,
        activity2: null,
        activity3: null
    };

    // Read all visible activity items and map them by slot
    const items = document.querySelectorAll('.activity-type-item');
    items.forEach(item => {
        const slot = item.getAttribute('data-activity-slot');
        const nameEl = item.querySelector('.activity-type-name');
        const name = nameEl ? nameEl.textContent.trim() : '';

        if (slot === '1') dto.activity1 = name || null;
        if (slot === '2') dto.activity2 = name || null;
        if (slot === '3') dto.activity3 = name || null;
    });

    return dto;
}

/**
 * Saves the edited activity name by sending the full DTO to the backend.
 */
async function saveActivity() {
    const editInput = document.getElementById('activity-edit-input');
    const editSlotInput = document.getElementById('activity-edit-slot');

    const newName = editInput ? editInput.value.trim() : '';
    const slot = editSlotInput ? editSlotInput.value : '';

    if (!newName) {
        if (editInput) {
            editInput.style.borderColor = '#ef4444';
            setTimeout(() => {
                editInput.style.borderColor = '';
            }, 300);
        }
        return;
    }

    if (!slot) return;

    // Build the full DTO with current values
    const dto = buildActivitiesDTO();

    // Update the edited slot with the new name
    if (slot === '1') dto.activity1 = newName;
    if (slot === '2') dto.activity2 = newName;
    if (slot === '3') dto.activity3 = newName;

    try {
        const response = await fetch(`${ACTIVITIES_ENDPOINT}/set/user/activities`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dto)
        });

        if (!response.ok) {
            throw new Error(`Failed to save activity: ${response.status}`);
        }

        // Reload the page after successful save
        window.location.reload();

    } catch (err) {
        console.error('Save activity failed:', err);
    }
}


// ============================================================
// KEYBOARD SUPPORT
// ============================================================

document.addEventListener('DOMContentLoaded', function() {
    // Allow pressing Enter in the edit input to save
    const editInput = document.getElementById('activity-edit-input');
    if (editInput) {
        editInput.addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                saveActivity();
            }
            if (e.key === 'Escape') {
                hideEditForm();
            }
        });
    }
});