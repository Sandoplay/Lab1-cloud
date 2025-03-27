package edu.levytskyi.lab1cloud.repository; // Заміни на свій пакет

import edu.levytskyi.lab1cloud.model.Item; // Заміни на свій пакет
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // Spring Data JPA автоматично реалізує базові CRUD методи:
    // save(), findById(), findAll(), deleteById(), etc.
}