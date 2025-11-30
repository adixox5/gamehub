/* ======================================================
   SKRYPT "MAIN" (Uruchamiany na każdej stronie)
   ====================================================== */

document.addEventListener('DOMContentLoaded', () => {

    /* --- LOGIKA PRZEŁĄCZNIKA MOTYWU (DARK MODE) --- */
    
    const themeToggleButton = document.getElementById('theme-toggle-btn');
    const body = document.body;
    
    if (themeToggleButton) {
        const themeIcon = themeToggleButton.querySelector('i'); 

        // Funkcja do ustawiania i zapisywania motywu
        function setCurrentTheme(theme) {
            if (theme === 'dark') {
                body.classList.add('dark-mode');
                themeIcon.classList.remove('fa-moon');
                themeIcon.classList.add('fa-sun');
                localStorage.setItem('theme', 'dark');
            } else {
                body.classList.remove('dark-mode');
                themeIcon.classList.remove('fa-sun');
                themeIcon.classList.add('fa-moon');
                localStorage.setItem('theme', 'light');
            }
        }

        // Sprawdzenie motywu przy ładowaniu strony
        const savedTheme = localStorage.getItem('theme');
        if (savedTheme) {
            setCurrentTheme(savedTheme);
        } else {
            setCurrentTheme('light'); // Domyślny motyw
        }

        // Logika kliknięcia w przełącznik
        themeToggleButton.addEventListener('click', () => {
            if (body.classList.contains('dark-mode')) {
                setCurrentTheme('light');
            } else {
                setCurrentTheme('dark');
            }
        });
    }

    
    /* --- LOGIKA PASKA NAWIGACJI (Logowanie i Role) --- */
    
    const userSection = document.getElementById('navbar-user-section');
    const token = localStorage.getItem('user_token');
    const userName = localStorage.getItem('user_name'); 
    const userRole = localStorage.getItem('user_role'); 

    if (token && userName && userSection) {
        // Użytkownik jest zalogowany
        userSection.innerHTML = `
            <span>Witaj, ${userName}!</span>
            <button id="logout-btn" class="btn btn-secondary">Wyloguj</button>
        `;

        // Logika wylogowania
        document.getElementById('logout-btn').addEventListener('click', () => {
            localStorage.removeItem('user_token');
            localStorage.removeItem('user_name'); 
            localStorage.removeItem('user_role'); 
            
            // Poprawne przekierowanie
            if (window.location.pathname.includes('/pages/')) {
                 window.location.href = '../index.html';
            } else {
                 window.location.href = '/index.html';
            }
        });

        // Logika pokazywania linków dla ról
        if (userRole === 'CREATOR' || userRole === 'MODERATOR') {
            const addGameLink = document.getElementById('nav-link-add-game');
            if (addGameLink) addGameLink.style.display = 'inline-block';
        }
        
        if (userRole === 'MODERATOR') {
            const adminPanelLink = document.getElementById('nav-link-admin-panel');
            if (adminPanelLink) adminPanelLink.style.display = 'inline-block';
        }
    }
    
    
    /* --- LOGIKA DROPDOWNU KATEGORII --- */

    const categoryDropdown = document.getElementById('category-dropdown');
    
    if (categoryDropdown) {
        const categoryBtn = document.getElementById('category-dropdown-btn');
        const categoryContent = document.getElementById('category-dropdown-content');
        const categoryBtnText = document.getElementById('category-btn-text');

        // 1. Logika otwierania/zamykania menu po kliknięciu
        categoryBtn.addEventListener('click', (event) => {
            event.stopPropagation();
            categoryDropdown.classList.toggle('active');
        });

        // 2. Logika wyboru kategorii
        categoryContent.addEventListener('click', (event) => {
            if (event.target.tagName === 'A') {
                event.preventDefault();
                const selectedText = event.target.textContent;
                const selectedCategory = event.target.getAttribute('data-category');
                
                categoryBtnText.textContent = selectedText;
                
                // TODO: filtrowanie gier
                console.log('Wybrana kategoria:', selectedCategory); 
                
                categoryDropdown.classList.remove('active');
            }
        });

        // 3. Logika zamykania menu po kliknięciu gdziekolwiek indziej
        window.addEventListener('click', () => {
            if (categoryDropdown.classList.contains('active')) {
                categoryDropdown.classList.remove('active');
            }
        });
    }
    
});