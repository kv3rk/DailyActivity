// ============================================================
// ACTIVITY TYPES
// ============================================================

/**
 * Shows the edit form below the activity list, pre-filled with the current name.
 * Only one edit form can be open at a time.
 */
function editActivity(activityId) {
    const item = document.querySelector(`.activity-type-item[data-activity-id="${activityId}"]`);
    if (!item) return;

    // Get current activity name
    const nameEl = item.querySelector('.activity-type-name');
    const currentName = nameEl ? nameEl.textContent.trim() : '';

    // Hide any previously open edit form
    hideEditForm();

    // Show the edit form
    const editWrap = document.getElementById('activity-edit-wrap');
    const editInput = document.getElementById('activity-edit-input');
    const editIdInput = document.getElementById('activity-edit-id');

    if (editWrap && editInput && editIdInput) {
        editWrap.style.display = 'block';
        editInput.value = currentName;
        editInput.focus();
        editIdInput.value = activityId;

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
    const editIdInput = document.getElementById('activity-edit-id');

    if (editWrap) editWrap.style.display = 'none';
    if (editInput) editInput.value = '';
    if (editIdInput) editIdInput.value = '';

    // Remove editing highlight from all items
    document.querySelectorAll('.activity-type-item.editing').forEach(el => {
        el.classList.remove('editing');
    });
}

/**
 * Saves the edited activity name.
 * Placeholder — replace with actual endpoint call when backend is ready.
 */
function saveActivity() {
    const editInput = document.getElementById('activity-edit-input');
    const editIdInput = document.getElementById('activity-edit-id');

    const newName = editInput ? editInput.value.trim() : '';
    const activityId = editIdInput ? editIdInput.value : '';

    if (!newName) {
        // Shake the input or show some visual feedback
        if (editInput) {
            editInput.style.borderColor = '#ef4444';
            setTimeout(() => {
                editInput.style.borderColor = '';
            }, 300);
        }
        return;
    }

    if (!activityId) return;

    // TODO: Replace with actual API call
    // Example:
    // fetch(`/api/activities/${activityId}`, {
    //     method: 'PUT',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({ name: newName })
    // })
    // .then(response => {
    //     if (response.ok) {
    //         window.location.reload();
    //     }
    // });

    // For now: update the DOM and reload
    const item = document.querySelector(`.activity-type-item[data-activity-id="${activityId}"]`);
    if (item) {
        const nameEl = item.querySelector('.activity-type-name');
        if (nameEl) {
            nameEl.textContent = newName;
        }
    }

    hideEditForm();

    // Reload the page (as requested)
    // window.location.reload();
}

/**
 * Deletes an activity item and renumbers the remaining ones.
 * Placeholder — replace with actual endpoint call when backend is ready.
 */
function deleteActivity(activityId) {
    const item = document.querySelector(`.activity-type-item[data-activity-id="${activityId}"]`);
    if (!item) return;

    // Optional: confirm before delete
    // if (!confirm('Are you sure you want to delete this activity?')) return;

    // TODO: Replace with actual API call
    // Example:
    // fetch(`/api/activities/${activityId}`, { method: 'DELETE' })
    // .then(response => {
    //     if (response.ok) {
    //         window.location.reload();
    //     }
    // });

    // For now: remove from DOM and renumber
    item.remove();
    renumberItems();
    hideEditForm();

    // Check if list is empty
    const list = document.getElementById('activity-types-list');
    if (list && list.children.length === 0) {
        showEmptyState();
    }
}

/**
 * Adds a new activity.
 * Placeholder — replace with actual endpoint call when backend is ready.
 */
function addActivity() {
    const input = document.getElementById('activity-add-input');
    const name = input ? input.value.trim() : '';

    if (!name) {
        if (input) {
            input.style.borderColor = '#ef4444';
            setTimeout(() => {
                input.style.borderColor = '';
            }, 300);
        }
        return;
    }

    // TODO: Replace with actual API call
    // Example:
    // fetch(`/api/activities`, {
    //     method: 'POST',
    //     headers: { 'Content-Type': 'application/json' },
    //     body: JSON.stringify({ name: name })
    // })
    // .then(response => {
    //     if (response.ok) {
    //         window.location.reload();
    //     }
    // });

    // For now: clear input and reload the page to simulate
    if (input) input.value = '';
    // window.location.reload();
}

/**
 * Renumbers all activity items sequentially (1, 2, 3, ...).
 * Call this after any item is deleted.
 */
function renumberItems() {
    const list = document.getElementById('activity-types-list');
    if (!list) return;

    const items = list.querySelectorAll('.activity-type-item');
    items.forEach((item, index) => {
        const numberEl = item.querySelector('.activity-type-number');
        if (numberEl) {
            numberEl.textContent = (index + 1).toString();
        }
    });
}

/**
 * Shows an empty state message when all activities are deleted.
 * The Add Activity button remains visible below.
 */
function showEmptyState() {
    const list = document.getElementById('activity-types-list');
    if (!list) return;

    list.innerHTML = `
        <div class="activity-types-empty">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"></circle>
                <line x1="8" y1="12" x2="16" y2="12"></line>
            </svg>
            <p>No activity types configured.</p>
        </div>
    `;
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

    // Allow pressing Enter in the add input to add activity
    const addInput = document.getElementById('activity-add-input');
    if (addInput) {
        addInput.addEventListener('keydown', function(e) {
            if (e.key === 'Enter') {
                e.preventDefault();
                addActivity();
            }
        });
    }
});