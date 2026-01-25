package com.gamehub.demo.repository;

import com.gamehub.demo.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    // Pobierz gry z danej kategorii
    List<Game> findByCategory(String category);

    // Wyszukiwanie po tytule (niezależnie od wielkości liter)
    List<Game> findByTitleContainingIgnoreCase(String title);

    // Wyszukiwanie po tytule w ramach konkretnej kategorii
    List<Game> findByCategoryAndTitleContainingIgnoreCase(String category, String title);

    // Pobierz listę wszystkich dostępnych kategorii (bez powtórzeń)
    @Query("SELECT DISTINCT g.category FROM Game g")
    List<String> findDistinctCategories();
}