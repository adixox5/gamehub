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

   /* ==============================================
          LOGIKA KATEGORII I BAZA GIER
   ============================================== */

       const gamesDatabase = [
               {
                   id: 1,
                   title: "Cyberpunk City",
                   category: "akcja",
                   description: "Wkrocz do neonowego miasta przyszłości i walcz o przetrwanie.",
                   image: "https://placehold.co/280x180/1a1a1a/FFF?text=Action",
                   link: "/pages/game.html?id=1"
               },
               {
                   id: 2,
                   title: "Super Rally",
                   category: "wyscigi",
                   description: "Poczuj prędkość na szutrowych trasach w walce o puchar.",
                   image: "https://placehold.co/280x180/e74c3c/FFF?text=Rally",
                   link: "/pages/game.html?id=2"
               },
               {
                   id: 3,
                   title: "Chess Master",
                   category: "logiczne",
                   description: "Klasyczna gra w szachy. Sprawdź swoje umiejętności taktyczne.",
                   image: "https://placehold.co/280x180/f1c40f/000?text=Chess",
                   link: "/pages/game.html?id=3"
               },
               {
                   id: 4,
                   title: "Space Shooter",
                   category: "akcja",
                   description: "Broń galaktyki przed inwazją obcych statków.",
                   image: "https://placehold.co/280x180/2980b9/FFF?text=Space",
                   link: "/pages/game.html?id=4"
               },
               {
                   id: 5,
                   title: "Fantasy RPG",
                   category: "rpg",
                   description: "Epicka przygoda w świecie magii, smoków i rycerzy.",
                   image: "https://placehold.co/280x180/8e44ad/FFF?text=RPG",
                   link: "/pages/game.html?id=5"
               },
               {
                       id: 6,
                       title: "Hextris",
                       category: "logiczne",
                       description: "Obracaj sześciokąt i dopasowuj kolory w tej szybkiej grze logicznej.",
                       image: "https://placehold.co/280x180/2c3e50/FFF?text=Hextris",
                       link: "/pages/game.html?id=hextris"
               },
               {
                           id: 7,
                           title: "Pacman",
                           category: "akcja",
                           description: "Legenda gier wideo powraca w wersji HTML5.",
                           image: "https://placehold.co/280x180/f1c40f/000?text=Pacman",
                           link: "/game?id=pacman"
               },
               {
                       id: 8,
                       title: "Tetris",
                       category: "logiczne",
                       description: "Układaj spadające klocki i bij rekordy w tej ponadczasowej grze.",
                       image: "https://placehold.co/280x180/9b59b6/FFF?text=Tetris",
                       link: "/pages/game.html?id=tetris"
               },
               {
                        id: 9,
                        title: "0h h1",
                        category: "logiczne",
                        description: "Prosta, ale wymagająca gra logiczna. Nie pozwól na trzy takie same kolory obok siebie!",
                        image: "https://placehold.co/280x180/e74c3c/3498db?text=0h+h1", // Możesz zmienić na screen z gry
                        link: "/pages/game.html?id=0hh1"
               }
           ];

       // Funkcja uruchamiana automatycznie, jeśli jesteśmy na stronie kategorii
       function initCategoryPage() {
           const container = document.getElementById('games-grid-container');

           // Jeśli nie ma kontenera gier, przerwanie
           if (!container) return;

           // 1. Pobieramy parametr 'kategoria' z URL
           const urlParams = new URLSearchParams(window.location.search);
           const categoryParam = urlParams.get('kategoria'); // Szuka ?kategoria=...

           const titleElement = document.getElementById('category-title');
           let gamesToShow = gamesDatabase;

           // 2. Logika zmiany tytułu i filtrowania
           if (categoryParam) {
               // Zmiana tytułu na nazwę kategorii (np. AKCJA)
               if (titleElement) {
                   titleElement.textContent = categoryParam.toUpperCase();
               }
               // Filtrowanie gier
               gamesToShow = gamesDatabase.filter(g => g.category === categoryParam);
           } else {
               // Jeśli brak parametru, zostaje domyślny tytuł
               if (titleElement) {
                   titleElement.textContent = "WSZYSTKIE GRY";
               }
           }

           // 3. Renderowanie gier
           container.innerHTML = "";

                   if (gamesToShow.length === 0) {
                       container.innerHTML = "<p style='text-align:center; width:100%; font-size:1.2rem; margin-top:20px;'>Brak gier w tej kategorii.</p>";
                       return;
                   }

                   gamesToShow.forEach(game => {
                               const card = document.createElement('div');
                               card.className = 'game-card';

                               card.style.width = "280px";
                               card.style.flex = "0 0 auto";
                               card.style.marginBottom = "20px";

                               card.innerHTML = `
                                   <img src="${game.image}" alt="${game.title}">
                                   <h3>${game.title}</h3>
                                   <p class="game-desc">${game.description}</p>
                                   <a href="${game.link}" class="btn">Graj</a>
                               `;
                               container.appendChild(card);
                           });
       }

       // Wywoływanie funkcji
       initCategoryPage();

   });
   /* ======================================================
      OBSŁUGA WYSZUKIWARKI GIER (SEARCH BAR)
      ====================================================== */
   document.addEventListener('DOMContentLoaded', () => {
       const searchForm = document.getElementById('search-form');
       const searchInput = document.getElementById('search-input');

       // Lista gier do wyszukiwania (Nazwa -> ID)
       // ID musi pasować do tego w pliku game.js!
       const searchableGames = [
           { name: "2048", id: "2048" },
           { name: "logiczna", id: "2048" },
           { name: "cyferki", id: "2048" },
           { name: "slot", id: "slot" },
           { name: "bandyta", id: "slot" },
           { name: "kasyno", id: "slot" },
           { name: "maszyna", id: "slot" },
           { name: "hextris", id: "hextris" },
           { name: "sześciokąt", id: "hextris" },
           { name: "logiczna", id: "hextris" },
           { name: "pacman", id: "pacman" },
           { name: "duszki", id: "pacman" },
           { name: "zjadacz kropek", id: "pacman" },
           { name: "tetris", id: "tetris" },
           { name: "klocki", id: "tetris" },
           { name: "układanka", id: "tetris" },
           { name: "0hh1", id: "0hh1" },
           { name: "ohhi", id: "0hh1" },
           { name: "kolory", id: "0hh1" }
       ];

       if (searchForm) {
           searchForm.addEventListener('submit', (e) => {
               e.preventDefault(); // Zatrzymuje przeładowanie strony

               const query = searchInput.value.toLowerCase().trim();

               if (query.length === 0) return;

               // Szukanie gry (sprawdza czy nazwa zawiera wpisaną frazę)
               const foundGame = searchableGames.find(game => game.name.includes(query));

               if (foundGame) {
                   // Ustalanie ścieżki (zależnie czy jesteśmy w folderze pages czy w root)
                   const prefix = window.location.pathname.includes('/pages/') ? '' : 'pages/';

                   // Przekierowanie do gry
                   window.location.href = `${prefix}game.html?id=${foundGame.id}`;
               } else {
                   alert("Nie znaleziono gry o nazwie: " + query);
               }
           });
       }
   });