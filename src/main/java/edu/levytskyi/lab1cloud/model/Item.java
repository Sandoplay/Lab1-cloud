package edu.levytskyi.lab1cloud.model; // Заміни на свій пакет

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Entity
@Table(name = "items")
@Data // Lombok: генерує getters, setters, toString, equals, hashCode
@NoArgsConstructor // для JPA
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Конструктор для створення без ID
    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}