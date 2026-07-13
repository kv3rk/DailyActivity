document.addEventListener('DOMContentLoaded', function () {
    highlightCurrentTheme();
});

function setTheme(themeValue) {
    fetch('/daily/settings/set/theme', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ theme: themeValue })
    })
        .then(response => {
            if (response.ok) {
                location.reload();
            }
        })
        .catch(error => console.error('Error setting theme:', error));
}

function highlightCurrentTheme() {
    fetch('/daily/settings/get/theme')
        .then(response => response.json())
        .then(data => {
            const currentTheme = data.theme;

            document.querySelectorAll('.theme-btn').forEach(btn => {
                btn.classList.remove('active');
            });

            if (currentTheme === 'dark') {
                const darkBtn = document.getElementById('theme-btn-dark');
                if (darkBtn) darkBtn.classList.add('active');
            } else {
                const lightBtn = document.getElementById('theme-btn-light');
                if (lightBtn) lightBtn.classList.add('active');
            }
        })
        .catch(error => console.error('Error getting theme:', error));
}