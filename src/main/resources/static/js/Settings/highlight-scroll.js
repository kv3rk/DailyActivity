const sections = document.querySelectorAll('.settings-section');
const navItems = document.querySelectorAll('.settings-nav-item');

function highlightNav() {
    let current = '';
    sections.forEach(section => {
        const sectionTop = section.offsetTop;
        if (scrollY >= sectionTop - 120) {
            current = section.getAttribute('id');
        }
    });

    navItems.forEach(item => {
        item.classList.remove('active');
        if (item.getAttribute('href') === '#' + current) {
            item.classList.add('active');
        }
    });
}

window.addEventListener('scroll', highlightNav);
highlightNav();


const volumeSlider = document.getElementById('volume-slider');
const volumeValue = document.getElementById('volume-value');
if (volumeSlider) {
    volumeSlider.addEventListener('input', (e) => {
        volumeValue.textContent = e.target.value + '%';
    });
}