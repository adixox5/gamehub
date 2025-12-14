document.addEventListener('DOMContentLoaded', () => {

    // --- BAZA GIER ---
    // Tutaj definiujesz, jakie gry są dostępne i gdzie leżą ich pliki index.html
    const gamesDatabase = {
        "2048": {
            title: "2048",
            // Ścieżka relatywna z folderu 'pages' do folderu 'games'
            path: "../games/2048/index.html",
            description: "Połącz kafelki z tymi samymi liczbami, aby uzyskać wynik 2048! Użyj strzałek do sterowania."
        },
        "slot": {
            title: "Jednoręki Bandyta",
            path: "../games/slot/index.html",
            description: "Sprawdź swoje szczęście w klasycznej maszynie losującej."
        }
        // Aby dodać nową grę, dopisz ją tutaj wg schematu:
        // "id_gry": { title: "Tytuł", path: "../games/folder_gry/index.html", description: "Opis" },
    };

    // --- POBIERANIE ID Z URL ---
    const urlParams = new URLSearchParams(window.location.search);
    const gameId = urlParams.get('id'); // np. "2048"

    // --- ELEMENTY DOM ---
    const iframe = document.getElementById('game-iframe');
    const titleHeader = document.getElementById('game-title-header');
    const descParagraph = document.getElementById('game-description');

    // --- LOGIKA ŁADOWANIA ---
    if (gameId && gamesDatabase[gameId]) {
        const gameData = gamesDatabase[gameId];

        // 1. Ustaw tytuł
        document.title = "GameHub - " + gameData.title;
        if(titleHeader) titleHeader.textContent = gameData.title;

        // 2. Ustaw opis
        if(descParagraph) descParagraph.textContent = gameData.description;

        // 3. Załaduj grę do ramki (IFRAME)
        if(iframe) iframe.src = gameData.path;

    } else {
        // Obsługa błędu (brak ID lub błędne ID)
        if(titleHeader) titleHeader.textContent = "Wybierz grę";
        if(descParagraph) descParagraph.textContent = "Nie wybrano gry lub gra nie istnieje. Wróć do strony głównej.";
        if(iframe) iframe.style.display = "none";
    }
});