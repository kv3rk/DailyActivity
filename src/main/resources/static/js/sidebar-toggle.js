function toggleSidebar() {
    document.getElementById('sidebar').classList.toggle('open');
    document.getElementById('sidebarOverlay').classList.toggle('open');
}

function closeSidebar() {
    document.getElementById('sidebar').classList.remove('open');
    document.getElementById('sidebarOverlay').classList.remove('open');
}

function highlightActivePage() {
    const currentPath = window.location.pathname;

    const pageMap = {
        '/': 'main',
        '/daily': 'main',
        '/daily/': 'main'
    };

    const activePage = pageMap[currentPath] || 'main';

    document.querySelectorAll('.sidebar-item').forEach(item => {
        item.classList.remove('active');
        if (item.dataset.page === activePage) {
            item.classList.add('active');
        }
    });
}

document.addEventListener('DOMContentLoaded', highlightActivePage);